package com.example.project.db

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel

class ElementViewModel : ViewModel() {
    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var number by mutableIntStateOf(1)
    var position by mutableStateOf("Striker")
    var isGood by mutableStateOf(false)
    var photoUri by mutableStateOf<Uri>(Uri.EMPTY)

    fun setValues(playerData: PlayerData) {
        name = playerData.text_name
        surname = playerData.text_surname
        number = playerData.item_number
        position = playerData.item_position
        isGood = playerData.good
        photoUri = playerData.photo_uri.toUri()
    }
}