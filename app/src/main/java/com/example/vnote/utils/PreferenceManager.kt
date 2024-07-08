package com.example.vnote.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.vnote.ui.MainActivity

class PreferenceManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var INSTANCE: PreferenceManager? = null

        fun getInstance(context: Context): PreferenceManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferenceManager(context).also { INSTANCE = it }
            }
        }
    }

    fun saveText(text: String) {
        sharedPreferences.edit().putString(MainActivity.TEXT_KEY, text).apply()
    }

    fun getText(): String? {
        return sharedPreferences.getString(MainActivity.TEXT_KEY, "")
    }

    fun saveImageUri(uri: String) {
        sharedPreferences.edit().putString(MainActivity.IMAGE_URI_KEY, uri).apply()
    }

    fun getImageUri(): String? {
        return sharedPreferences.getString(MainActivity.IMAGE_URI_KEY, null)
    }

    fun saveNotes(notes: String) {
        sharedPreferences.edit().putString(MainActivity.NOTES_KEY, notes).apply()
    }

    fun getNotes(): String? {
        return sharedPreferences.getString(MainActivity.NOTES_KEY, "")
    }
}
