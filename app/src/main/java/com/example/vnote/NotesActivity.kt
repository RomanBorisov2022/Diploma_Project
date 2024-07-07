package com.example.vnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vnote.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadNotes()
    }

    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE)
        val savedNotes = sharedPreferences.getString(MainActivity.NOTES_KEY, "")
        binding.notesTextView.text = savedNotes
    }
}
