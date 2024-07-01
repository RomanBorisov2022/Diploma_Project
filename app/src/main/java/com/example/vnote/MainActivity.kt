package com.example.vnote

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.content.Intent
import android.net.Uri
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val GALLERY_REQUEST_CODE = 123
    private lateinit var galleryPhotoManager: GalleryPhotoManager
    private lateinit var notesFileManager: NotesFileManager
    private lateinit var photoImageView: ImageView
    private lateinit var uploadPhotoButton: Button
    private lateinit var vehicleInfoEditText: EditText
    private lateinit var editInfoButton: Button
    private lateinit var createNoteButton: Button
    private lateinit var notesContainer: LinearLayout
    private lateinit var saveFileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Подготовка элементов разметки
        photoImageView = findViewById(R.id.photoImageView)
        uploadPhotoButton = findViewById(R.id.uploadPhotoButton)
        vehicleInfoEditText = findViewById(R.id.vehicleInfoEditText)
        editInfoButton = findViewById(R.id.editInfoButton)
        createNoteButton = findViewById(R.id.createNoteButton)
        notesContainer = findViewById(R.id.notesContainer)
        saveFileButton = findViewById(R.id.saveFileButton)

        galleryPhotoManager = GalleryPhotoManager(this, GALLERY_REQUEST_CODE)
        notesFileManager = NotesFileManager(this)

        // Обработка нажатия кнопки "Загрузить фото"
        uploadPhotoButton.setOnClickListener {
            galleryPhotoManager.choosePhotoFromGallery()
        }

        // Обработка нажатия кнопки "Создать заметку"
        createNoteButton.setOnClickListener {
            val noteText = "Новая заметка" // Ваш текст заметки
            val noteTextView = TextView(this)
            noteTextView.text = noteText
            notesContainer.addView(noteTextView)
            notesFileManager.saveNotesToFile(listOf(noteText), "notes.txt")
        }

        // Другие обработчики нажатий здесь...
    }

    // Метод для обработки выбора фотографии из галереи
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedImage = data?.data
            photoImageView.setImageURI(selectedImage) // установка выбранной фотографии в ImageView
        }
    }

    // Сохранение фотографии на устройстве
    private fun savePhotoToStorage(selectedImage: Uri?) {
        // Получение пути к выбранной фотографии
        val path = selectedImage?.path

        // Здесь добавьте логику сохранения пути к фотографии в файловой системе устройства или в базе данных
    }

    // Сохранение заметки в файле
    private fun saveNoteToFile(noteText: String, fileName: String) {
        try {
            val fileOutputStream = openFileOutput(fileName, Context.MODE_APPEND)
            fileOutputStream.write(noteText.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Загрузка всех заметок из файла
    private fun loadNotesFromFile(fileName: String) {
        val file = File(filesDir, fileName)
        try {
            val fileInputStream = FileInputStream(file)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                val noteTextView = TextView(this)
                noteTextView.text = line
                noteTextView.setOnClickListener {
                    // Обработка нажатия на заметку
                    Toast.makeText(this, "Нажата заметка: $line", Toast.LENGTH_SHORT).show()
                }
                notesContainer.addView(noteTextView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
