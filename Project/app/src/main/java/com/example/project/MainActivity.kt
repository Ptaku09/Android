package com.example.project

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project.db.PlayerRepo


class MainActivity : ComponentActivity() {
    private val REQUIRED_PERMISSIONS = mutableListOf(
        android.Manifest.permission.CAMERA,
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }.toTypedArray()

    private val REQUEST_CODE = 123

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetPackProjectTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (!allPermissionsGranted()) {
                        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE)

                        if (allPermissionsGranted()) {
                            PlayerRepo(this)
                            MyApp()
                        }
                    } else if (allPermissionsGranted()) {
                        PlayerRepo(this)
                        MyApp()
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        println(
            "Permission $it is granted: " + (ContextCompat.checkSelfPermission(
                baseContext,
                it
            ) == PackageManager.PERMISSION_GRANTED)
        )

        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
