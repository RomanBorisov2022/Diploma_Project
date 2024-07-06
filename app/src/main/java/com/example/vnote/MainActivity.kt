package com.example.vnote

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var secondTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userDescription: String
    private var imageUri: String? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = uri.toString()
            loadImageFromUri(uri)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imageUri", imageUri)
        outState.putString("userDescription", userDescription)
        outState.putString("textFromSecondTextView", secondTextView.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image)
        secondTextView = findViewById<TextView>(R.id.secondTextView)
        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)


        imageView.setOnClickListener {
            getContent.launch("image/*")
        }

        secondTextView.setOnClickListener {
            showDescriptionDialog()
        }

        userDescription = savedInstanceState?.getString("userDescription") ?: ""

        userDescription = sharedPreferences.getString("userDescription", "") ?: ""

        imageUri = savedInstanceState?.getString("imageUri")
        if (imageUri != null) {
            loadImageFromUri(Uri.parse(imageUri))
        }

        secondTextView.text = savedInstanceState?.getString("textFromSecondTextView", "")

        secondTextView.text = userDescription

    }

    override fun onStop() {
        super.onStop()
        // Сохранение информации в SharedPreferences
        val userDescription = secondTextView.text.toString()
        with(sharedPreferences.edit()) {
            putString("userDescription", userDescription)
            putString("imageUri", imageUri)
            apply()
        }
    }

    private fun loadImageFromUri(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
            imageUri = uri.toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun showDescriptionDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val editText = EditText(this)
        dialogBuilder.setTitle("Enter description")
        dialogBuilder.setView(editText)

        dialogBuilder.setPositiveButton("Save") { dialog, whichButton ->
            val userDescription = editText.text.toString()
            secondTextView.text = userDescription  // Обновляем текст в соответствующем TextView
            with(sharedPreferences.edit()) {
                putString("userDescription", userDescription)
                apply()
            }
        }

        dialogBuilder.setNegativeButton("Cancel") { dialog, whichButton -> }

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}