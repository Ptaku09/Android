package com.example.project.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val playerRepo = PlayerRepo.getInstance()
    private val _items = MutableStateFlow<List<PlayerData>>(emptyList())
    val items: StateFlow<List<PlayerData>> = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _items.value = playerRepo.getData() ?: emptyList()
        }
    }

    fun deleteItem(item: PlayerData) {
        viewModelScope.launch {
            playerRepo.deleteItem(item)
            fetchItems()
        }
    }
}