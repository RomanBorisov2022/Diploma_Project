package com.example.vnote.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vnote.databinding.ActivityNotesBinding
import com.example.vnote.utils.PreferenceManager

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager.getInstance(this)

        loadNotes()
    }

    private fun loadNotes() {
        val savedNotes = preferenceManager.getNotes()
        binding.notesTextView.text = savedNotes
    }
}
