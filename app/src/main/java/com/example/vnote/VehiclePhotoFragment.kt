package com.example.vnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class VehiclePhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Здесь можно создать и настроить представление фрагмента прямо из кода
        val view = ImageView(requireContext())
        // Настройка визуальных свойств и поведения фрагмента
        return view
    }
}