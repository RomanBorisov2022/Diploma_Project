package com.example.vnote

import android.content.Context
import java.io.File
import java.io.FileWriter

class NotesFileManager(private val context: Context) {
    fun saveNotesToFile(notes: List<String>, fileName: String) {
        val file = File(context.filesDir, fileName)

        FileWriter(file).use { writer ->
            for (note in notes) {
                writer.appendln(note)
            }
        }
    }
}
