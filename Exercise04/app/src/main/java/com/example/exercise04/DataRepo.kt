package com.example.exercise04

class DataRepo {
    private val size = 3
    private var dataList: MutableList<DataItem> = MutableList(size) { DataItem() }

    companion object {
        private var INSTANCE: DataRepo? = null

        fun getInstance(): DataRepo {
            if (INSTANCE == null) {
                INSTANCE = DataRepo()
            }

            return INSTANCE!!
        }
    }

    fun getData(): MutableList<DataItem> {
        return dataList
    }

    fun deleteItem(position: Int): Boolean {
        dataList.removeAt(position)
        return true
    }

    fun addItem(item: DataItem): Boolean {
        return dataList.add(item)
    }
}