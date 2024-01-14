package com.example.project.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.db.ElementViewModel
import com.example.project.db.PlayerData
import com.example.project.db.PlayerRepo

@Composable
fun DetailsScreen(
    navController: NavController,
    playerDataId: Int,
    myViewModel: ElementViewModel = viewModel()
) {
    val playerRepo = PlayerRepo.getInstance()
    val playerData: PlayerData = playerRepo.getItemById(playerDataId)!!

    myViewModel.setValues(playerData)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "-- ${myViewModel.number} --",
            fontSize = 50.sp,
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "${myViewModel.name} ${myViewModel.surname}",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = myViewModel.position.uppercase(),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "${if (myViewModel.isGood) "Good" else "Bad"} Player",
            style = MaterialTheme.typography.titleMedium,
            color = if (myViewModel.isGood) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = { navController.navigate("edit/${playerData.id}") }) {
            Text(text = "Update")
        }
    }
}