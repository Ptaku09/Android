package com.example.project.db

import android.content.Context

class PlayerRepo() {
    private var dataList: MutableList<PlayerData>? = null
    private lateinit var myDao: PlayerDao
    private lateinit var db: PlayerDB

    companion object {
        private var INSTANCE: PlayerRepo? = null

        fun getInstance(): PlayerRepo {
            return INSTANCE as PlayerRepo
        }

        private fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PlayerRepo(context, true)
            }
        }
    }

    private constructor(context: Context, isPrivate: Boolean) : this() {
        db = PlayerDB.getDatabase(context)!!
        myDao = db.myDao()
    }

    constructor(context: Context) : this() {
        init(context)
    }

    fun getData(): MutableList<PlayerData>? {
        dataList = myDao.getAllData()
        return dataList
    }

    fun addItem(item: PlayerData): Boolean {
        return myDao.insert(item) >= 0
    }

    fun deleteItem(item: PlayerData): Boolean {
        return myDao.delete(item) > 0
    }

    fun getItemById(itemId: Int): PlayerData? {
        return try {
            myDao.getItemById(itemId)
        } catch (e: Exception) {
            null
        }
    }

    fun updateItem(item: PlayerData) {
        myDao.update(item)
    }
}