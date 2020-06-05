package com.soha.listen

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_camera.*

class Camera : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTUR_CODE = 1001
    var image_rui: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        permissionToCamera()

    }



    private fun permissionToCamera(){

        //if sys OS is Marshmallow or above, we need to request permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ) {

                val permission = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                //permission already granted
                openCamera()
            }
        } else {
            //system OS is < marshmallow
            openCamera()
        }
    }

    //Open Camera
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")

        image_rui = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        //camera Intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui)
        startActivityForResult(cameraIntent, IMAGE_CAPTUR_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){

            IMAGE_CAPTUR_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    //set image captured to image
                    image_view.setImageURI(image_rui)
                }
            }
        }


    }


}
