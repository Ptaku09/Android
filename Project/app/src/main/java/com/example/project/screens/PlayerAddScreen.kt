package com.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.db.ElementViewModel
import com.example.project.db.PlayerData
import com.example.project.db.PlayerRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController, viewModel: ElementViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Name") },
            modifier = Modifier
                .padding(top = 16.dp)
        )

        TextField(
            value = viewModel.surname,
            onValueChange = { viewModel.surname = it },
            label = { Text("Surname") },
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Number: ${viewModel.number}",
            style = MaterialTheme.typography.titleMedium
        )

        Slider(
            value = viewModel.number.toFloat(),
            onValueChange = { viewModel.number = it.toInt() },
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
            selectedOption = viewModel.position,
            onOptionSelected = { viewModel.position = it })

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Is good?")
            Checkbox(
                checked = viewModel.isGood,
                onCheckedChange = { viewModel.isGood = it },
                enabled = true
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Spacer(modifier = Modifier.size(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Button(
                onClick = {
                    val item = PlayerData(
                        viewModel.name,
                        viewModel.surname,
                        viewModel.number,
                        viewModel.position,
                        viewModel.isGood
                    )
                    PlayerRepo.getInstance().addItem(item)
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


@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) }
                    )
                    .background(Color.Transparent)
                    .padding(16.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) },
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = option, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}