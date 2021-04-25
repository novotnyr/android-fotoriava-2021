package com.github.novotnyr.android.fotoriava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult

class MainActivity : AppCompatActivity() {
    private lateinit var cameraView: CameraView

    private lateinit var lastPhotoImageView: ImageView

    val photoViewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraView = findViewById(R.id.cameraView)
        cameraView.setLifecycleOwner(this)
        cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                result.toBitmap(lastPhotoImageView::setImageBitmap)
                photoViewModel.addPhoto(result.data)
            }
        })

        lastPhotoImageView = findViewById(R.id.lastPhotoImageView)
    }

    fun onTakePhotoClick(view: View) {
        cameraView.takePicture()
    }
}