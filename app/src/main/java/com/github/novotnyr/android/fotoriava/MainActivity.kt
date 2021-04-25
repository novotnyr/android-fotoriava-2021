package com.github.novotnyr.android.fotoriava

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.google.android.material.snackbar.Snackbar
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult

class MainActivity : AppCompatActivity() {
    private lateinit var cameraView: CameraView

    private lateinit var lastPhotoImageView: ImageView

    val photoViewModel: PhotoViewModel by viewModels()

    private val storagePermission = registerForActivityResult(RequestPermission()) { granted ->
        if (granted) {
            cameraView.takePicture()
        } else {
            Snackbar
                .make(cameraView,
                        "Fotoriava nemá povolenie zapisovať fotky do úložiska",
                    Snackbar.LENGTH_SHORT)
                .show()
        }
    }

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
        if (Build.VERSION.SDK_INT >= Q) {
            cameraView.takePicture()
        } else {
            when (checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)) {
                PERMISSION_GRANTED -> cameraView.takePicture()
                else -> onStorageRejected()
            }
        }
    }

    private fun onStorageRejected() {
        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
            Snackbar
                .make(cameraView,
                    "Potrebujeme sprístupniť fotky pre iné appky.",
                    Snackbar.LENGTH_SHORT)
                .setAction("Povoliť") {
                    storagePermission.launch(WRITE_EXTERNAL_STORAGE)
                }
                .show()
        } else {
            storagePermission.launch(WRITE_EXTERNAL_STORAGE)
        }
    }
}