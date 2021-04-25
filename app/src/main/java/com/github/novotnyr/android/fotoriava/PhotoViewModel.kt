package com.github.novotnyr.android.fotoriava

import android.app.Application
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel

class PhotoViewModel(private val app: Application) : AndroidViewModel(app) {
    private val externalImagesUri: Uri
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
}