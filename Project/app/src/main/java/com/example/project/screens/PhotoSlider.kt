package com.example.project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.project.db.getBooleanValue
import com.example.project.db.saveStringValue
import com.example.project.photos.PhotoRepo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PhotoSlider(clickedIndex: Int) {
    val context = LocalContext.current
    val photoRepo = PhotoRepo.getInstance(context)
    val photos = photoRepo.getSharedList(getBooleanValue(context, "external"))!!
    val pagerState = rememberPagerState(initialPage = clickedIndex)

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        count = photos.size
    ) { page ->
        val photo = photos[page]
        val bitmap = getBitmapFromUri(context, photo.curi, 2)

        if (bitmap != null) {
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Button(onClick = {
                    saveStringValue(
                        context,
                        "main_photo",
                        photo.curi.toString()
                    )
                }) {
                    Text(text = "Pick photo")
                }
            }
        }
    }
}