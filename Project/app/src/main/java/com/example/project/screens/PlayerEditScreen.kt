package com.example.project.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.db.ElementViewModel
import com.example.project.db.PlayerData
import com.example.project.db.PlayerRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navController: NavController,
    playerDataId: Int,
    viewModel: ElementViewModel = viewModel()
) {
    val context = LocalContext.current
    val playerRepo = PlayerRepo.getInstance()
    val playerData: PlayerData? = playerRepo.getItemById(playerDataId)

    if (playerData == null) {
        Toast.makeText(context, "Player not found", Toast.LENGTH_LONG).show()
        navController.popBackStack()
        return
    }

    val name = remember { mutableStateOf(playerData.text_name) }
    val surname = remember { mutableStateOf(playerData.text_surname) }
    val number = remember { mutableIntStateOf(playerData.item_number) }
    val position = remember { mutableStateOf(playerData.item_position) }
    val isGood = remember { mutableStateOf(playerData.good) }

    viewModel.photoUri = playerData.photo_uri.toUri()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.padding(top = 16.dp)
        )

        TextField(
            value = surname.value,
            onValueChange = { surname.value = it },
            label = { Text("Surname") },
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Number: ${number.intValue}",
            style = MaterialTheme.typography.titleMedium
        )

        Slider(
            value = number.intValue.toFloat(),
            onValueChange = { number.intValue = it.toInt() },
            valueRange = 0f..100f,
            modifier = Modifier.width(250.dp)
        )

        Text(
            text = "Position",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        RadioGroup(
            options = listOf("Striker", "Middle Fielder", "Defender", "Goalkeeper"),
            selectedOption = position.value,
            onOptionSelected = { position.value = it })

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Is good?")

            Checkbox(
                checked = isGood.value,
                onCheckedChange = { isGood.value = it },
                enabled = true
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Spacer(modifier = Modifier.size(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    val updatedItem = PlayerData(
                        name.value,
                        surname.value,
                        number.intValue,
                        position.value,
                        isGood.value
                    )
                    updatedItem.id = playerDataId
                    playerRepo.updateItem(updatedItem)
                    navController.popBackStack()
                },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text("Update")
            }
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}