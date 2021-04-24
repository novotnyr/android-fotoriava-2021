package com.github.novotnyr.android.fotoriava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.otaliastudios.cameraview.CameraView

class MainActivity : AppCompatActivity() {
    private lateinit var cameraView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraView = findViewById(R.id.cameraView)
        cameraView.setLifecycleOwner(this)
    }

    fun onTakePhotoClick(view: View) {
        cameraView.takePicture()
    }
}