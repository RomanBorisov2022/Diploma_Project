package com.example.vnote

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

class GalleryPhotoManager(private val activity: Activity, private val requestCode: Int) {
    fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, requestCode)
    }
}