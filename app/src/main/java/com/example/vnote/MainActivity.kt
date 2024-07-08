package com.example.vnote

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.vnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val SHARED_PREFS = "sharedPrefs"
        private const val TEXT_KEY = "text"
        private const val IMAGE_URI_KEY = "imageUri"
        const val NOTES_KEY = "notes"
        const val IMAGE_NOTE_KEY = "imageNote"  // New key for image note
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        binding.image.setOnClickListener { openGallery() }

        binding.addNoteButton.setOnClickListener { showAddNoteDialog() }
        binding.showNotesButton.setOnClickListener { showAllNotes() }
        binding.secondTextView.setOnClickListener { showEditImageNoteDialog() } // Set click listener for text view

        loadImage()
        loadImageNote()
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                saveImageUri(imageUri)
                loadImage()
            }
        }
    }

    private fun saveImageUri(imageUri: Uri) {
        sharedPreferences.edit().putString(IMAGE_URI_KEY, imageUri.toString()).apply()
    }

    private fun loadImage() {
        val imageUriString = sharedPreferences.getString(IMAGE_URI_KEY, null)
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            Glide.with(this).load(imageUri).into(binding.image)
        }
    }

    private fun loadImageNote() {
        val imageNote = sharedPreferences.getString(IMAGE_NOTE_KEY, "")
        binding.secondTextView.text = imageNote
    }

    private fun showEditImageNoteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.enter_note))

        val input = EditText(this)
        input.setText(sharedPreferences.getString(IMAGE_NOTE_KEY, ""))  // Load current text
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.save)) { _, _ ->
            val text = input.text.toString()
            saveImageNoteText(text)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun saveImageNoteText(text: String) {
        sharedPreferences.edit().putString(IMAGE_NOTE_KEY, text).apply()
        binding.secondTextView.text = text // Update the TextView with new text
    }

    private fun showAddNoteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.enter_note))

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.save)) { _, _ ->
            val text = input.text.toString()
            saveText(text)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun saveText(text: String) {
        val savedNotes = sharedPreferences.getString(NOTES_KEY, "")
        val updatedNotes = if (savedNotes.isNullOrEmpty()) {
            text
        } else {
            "$savedNotes\n$text"
        }
        sharedPreferences.edit().putString(NOTES_KEY, updatedNotes).apply()
    }

    private fun showAllNotes() {
        val intent = Intent(this, NotesActivity::class.java)
        startActivity(intent)
    }
}