package com.example.vnote.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.vnote.R
import com.example.vnote.databinding.ActivityMainBinding
import com.example.vnote.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager

    companion object {
        const val SHARED_PREFS = "sharedPrefs"
        const val TEXT_KEY = "text"
        const val IMAGE_URI_KEY = "imageUri"
        const val NOTES_KEY = "notes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager.getInstance(this)

        binding.image.setOnClickListener { openGallery() }
        binding.addNoteButton.setOnClickListener { showAddNoteDialog() }
        binding.showNotesButton.setOnClickListener { showAllNotes() }
        binding.secondTextView.setOnClickListener { showEditDescriptionDialog() } // New line for editing description

        loadImage()
        loadText()
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
                preferenceManager.saveImageUri(imageUri.toString())
                loadImage()
            }
        }
    }

    private fun loadImage() {
        val imageUriString = preferenceManager.getImageUri()
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            Glide.with(this).load(imageUri).into(binding.image)
        }
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

    private fun showEditDescriptionDialog() { // New method for editing description
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.enter_description))

        val input = EditText(this)
        input.setText(preferenceManager.getText()) // Load current description
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.save)) { _, _ ->
            val text = input.text.toString()
            preferenceManager.saveText(text)
            binding.secondTextView.text = text // Update the description view
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun loadText() {
        val savedText = preferenceManager.getText()
        binding.secondTextView.text = savedText
    }

    private fun saveText(text: String) {
        val savedNotes = preferenceManager.getNotes()
        val updatedNotes = if (savedNotes.isNullOrEmpty()) {
            text
        } else {
            "$savedNotes\n$text"
        }
        preferenceManager.saveNotes(updatedNotes)
    }

    private fun showAllNotes() {
        val intent = Intent(this, NotesActivity::class.java)
        startActivity(intent)
    }
}
