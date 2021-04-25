package com.github.novotnyr.android.fotoriava

import android.app.Application
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.contentValuesOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PhotoViewModel(private val app: Application) : AndroidViewModel(app) {
    fun addPhoto(photoBuffer: ByteArray) = viewModelScope.launch(IO) {
        val photoData = contentValuesOf(
            MediaStore.Images.ImageColumns.DESCRIPTION to "Emulated 3D Scene",
        )
        val photoUri = app.contentResolver
            .insert(externalImagesUri, photoData)

        photoUri?.let {
            app.contentResolver
                .openOutputStream(photoUri)
                ?.write(photoBuffer)
        }
    }

    private val externalImagesUri: Uri
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
}