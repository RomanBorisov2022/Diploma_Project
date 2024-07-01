package com.example.vnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.example.vnote.R

class NotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Здесь можно создать и настроить представление фрагмента прямо из кода
        val view = ScrollView(requireContext())
        // Настройка визуальных свойств и поведения фрагмента
        return view
    }
}