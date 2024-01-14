package com.example.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.db.ListViewModel
import com.example.project.db.PlayerData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    val listViewModel = remember { ListViewModel() }
    val items by listViewModel.items.collectAsState()

    @SuppressLint("RememberReturnType")
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ListItem(item: PlayerData, onClick: () -> Unit) {
        val showDialog = remember { mutableStateOf(false) }
        val green = Color.Green.copy(alpha = 0.3f)
        val red = Color.Red.copy(alpha = 0.5f)
        val yellow = Color.Yellow.copy(alpha = 0.5f)
        val blue = Color.Blue.copy(alpha = 0.5f)
        val color =
            when (item.item_position) {
                "Goalkeeper" -> red
                "Defender" -> green
                "Middle Fielder" -> yellow
                else -> blue
            }

        Row(
            modifier = Modifier
                .combinedClickable(onClick = onClick, onLongClick = { showDialog.value = true })
                .fillMaxWidth()
                .background(Color.White)
                .padding(5.dp)
                .clip(RoundedCornerShape(15.dp))
                .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
                .background(color)
                .padding(top = 5.dp, end = 15.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.item_number.toString(),
                fontSize = 50.sp,
                modifier = Modifier.width(120.dp),
                textAlign = TextAlign.Center
            )

            Column {
                Text(
                    text = item.text_name,
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = item.text_surname.uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Text(
                text = item.item_position.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        if (showDialog.value) {
            ConfirmDeleteDialog(
                showDialog = true,
                onConfirm = {

                    listViewModel.deleteItem(item)
                    showDialog.value = false
                },
                onDismiss = { showDialog.value = false }
            )
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                text = { Text("Add") },
                onClick = { navController.navigate("add") },
                modifier = Modifier
                    .padding(16.dp)
            )
        },
    ) {
        LazyColumn {
            items(items) { item ->
                ListItem(item,
                    onClick = { navController.navigate("details/${item.id}") }
                )
            }
        }
    }
}

@Composable
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Confirm Delete") },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("No")
                }
            }
        )
    }
}
