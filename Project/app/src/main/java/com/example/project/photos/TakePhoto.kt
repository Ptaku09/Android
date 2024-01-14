package com.example.project.photos

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.project.BuildConfig
import com.example.project.R
import com.example.project.db.ElementViewModel
import com.example.project.screens.RadioGroup
import com.example.project.screens.getBitmapFromUri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var temporaryFileUri: Uri
var temporaryFile: File = File("")
var directory by mutableStateOf("External")

@Composable
fun TakePhoto(viewModel: ElementViewModel) {
    val context = LocalContext.current
    val photoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                Toast.makeText(context, "Photo TAKEN", Toast.LENGTH_LONG).show()
                viewModel.photoUri = temporaryFileUri
            } else {
                Toast.makeText(context, "Photo NOT taken!", Toast.LENGTH_LONG).show()
            }
        }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 15.dp)) {
        Button(onClick = {
            temporaryFileUri = getNewFileUri(context)
            try {
                photoLauncher.launch(temporaryFileUri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "CAMERA DOESN'T WORK!", Toast.LENGTH_LONG).show()
            }
        }) {
            Text("Take photo")
        }
    }

    PhotoPreview(uri = viewModel.photoUri)

    DestinationChooser()
}

@Composable
fun PhotoPreview(uri: Uri) {
    val bitmap = getBitmapFromUri(LocalContext.current, uri, 2)

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .padding(16.dp)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .padding(16.dp)
        )
    }
}

@Composable
fun DestinationChooser() {
    Text(
        text = "Memory:",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )
    RadioGroup(
        options = listOf("External", "Internal"),
        selectedOption = directory,
        onOptionSelected = { directory = it })
}

fun savePhoto(context: Context, viewModel: ElementViewModel) {
    if (directory == "External")
        saveImageToExternalStorage(context, viewModel)
    else
        saveImageToInternalStorage(context, viewModel)
}


fun getNewFileUri(context: Context): Uri {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile(
        "IMG_${timeStamp}_",
        ".jpg",
        storageDir
    )

    temporaryFile = imageFile

    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}

fun saveImageToExternalStorage(context: Context, viewModel: ElementViewModel) {
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val newFile = File(externalStorageDir, temporaryFile.name)

        viewModel.photoUri = Uri.fromFile(newFile)

        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null

        try {
            fis = FileInputStream(temporaryFile)
            fos = FileOutputStream(newFile)

            val buffer = ByteArray(1024)
            var length: Int
            while (fis.read(buffer).also { length = it } > 0) {
                fos.write(buffer, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fis?.close()
            fos?.close()
        }

        temporaryFile.delete()
    } else {
        Toast.makeText(context, "External storage not available!", Toast.LENGTH_LONG)
            .show()
    }
}

fun saveImageToInternalStorage(context: Context, viewModel: ElementViewModel) {
    val internalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
    val newFile = File(internalStorageDir, temporaryFile.name)

    viewModel.photoUri = Uri.fromFile(newFile)

    var fis: FileInputStream? = null
    var fos: FileOutputStream? = null

    try {
        fis = FileInputStream(temporaryFile)
        fos = FileOutputStream(newFile)

        val buffer = ByteArray(1024)
        var length: Int

        while (fis.read(buffer).also { length = it } > 0) {
            fos.write(buffer, 0, length)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fis?.close()
        fos?.close()
    }

    temporaryFile.delete()
}
