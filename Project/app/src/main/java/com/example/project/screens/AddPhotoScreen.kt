package com.example.project.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.db.ElementViewModel
import com.example.project.photos.TakePhoto
import com.example.project.photos.savePhoto


@Composable
fun AddPhotoScreen(navController: NavController, viewModel: ElementViewModel = viewModel()) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TakePhoto(viewModel)

        Spacer(modifier = Modifier.size(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    savePhoto(context, viewModel)
                    navController.popBackStack()
                },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text("Save")
            }

            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text("Cancel")
            }
        }
    }
}
