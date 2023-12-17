package com.example.exercise04

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class ImageRepository {
    lateinit var uri: Uri

    @SuppressLint("Recycle")
    fun getSharedList(): MutableList<ImageItem>? {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver: ContentResolver = ctx.contentResolver

        sharedStoreList?.clear()

        val cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Log.e("ImageRepository", "Cursor is null")
        } else if (!cursor.moveToFirst()) {
            Log.e("ImageRepository", "Cursor is empty")
        } else {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            do {
                val thisId = cursor.getLong(idColumn)
                val thisName = cursor.getString(nameColumn)

                val thisContentUri = ContentUris.withAppendedId(uri, thisId)
                val thisUriPath = thisContentUri.toString()
                sharedStoreList?.add(
                    ImageItem(
                        thisName,
                        thisUriPath,
                        "No path yet",
                        thisContentUri
                    )
                )
            } while (cursor.moveToNext())
        }
        return sharedStoreList
    }

    fun getAppList(): MutableList<ImageItem>? {
        val dir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        dir?.listFiles()
        appStoreList?.clear()
        if (dir?.isDirectory == true) {
            val fileList = dir.listFiles()
            if (fileList != null) {
                for (value in fileList) {
                    val fileName = value.name
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                        fileName.endsWith(".png") || fileName.endsWith(".gif")
                    ) {
                        val tmpUri = FileProvider.getUriForFile(
                            ctx,
                            "com.example.exercise04.provider", value
                        )
                        appStoreList?.add(
                            ImageItem(
                                fileName, value.toURI().path,
                                value.absolutePath, tmpUri
                            )
                        )
                    }
                }
            }
        }
        return appStoreList
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: ImageRepository? = null

        @SuppressLint("StaticFieldLeak")
        private lateinit var ctx: Context
        private var sharedStoreList: MutableList<ImageItem>? = null
        private var appStoreList: MutableList<ImageItem>? = null

        fun getInstance(ctx: Context): ImageRepository {
            if (INSTANCE == null) {
                INSTANCE = ImageRepository()
                sharedStoreList = mutableListOf<ImageItem>()
                appStoreList = mutableListOf<ImageItem>()
                this.ctx = ctx
            }

            return INSTANCE as ImageRepository
        }
    }
}