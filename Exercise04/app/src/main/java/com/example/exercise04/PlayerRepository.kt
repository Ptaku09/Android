package com.example.exercise04

import android.content.Context

class PlayerRepository(context: Context) {
    private var dataList: MutableList<DBPlayer>? = null
    private var playerDao: PlayerDao? = null
    private var db: PlayerDB? = null

    companion object {
        private var INSTANCE: PlayerRepository? = null

        fun getInstance(context: Context): PlayerRepository? {
            if (INSTANCE == null) {
                INSTANCE = PlayerRepository(context)
            }

            return INSTANCE as PlayerRepository
        }
    }

    init {
        db = PlayerDB.getDatabase(context)!!
        playerDao = db?.playerDao()!!

        addPlayer(
            DBPlayer(
                "Lionel",
                "Messi",
                "The best player in the world",
                "10",
                "Striker",
                true
            )
        )

        addPlayer(
            DBPlayer(
                "Robert",
                "Lewandowski",
                "The best striker in the world",
                "9",
                "Striker",
                true
            )
        )

        addPlayer(
            DBPlayer(
                "Pablo",
                "Gavi",
                "Fighter",
                "6",
                "Middle fielder",
                true
            )
        )
    }

    fun getData(): MutableList<DBPlayer>? {
        dataList = playerDao?.getAll()

        return dataList
    }

    fun addPlayer(player: DBPlayer) {
        playerDao?.insert(player)
    }

    fun deletePlayer(player: DBPlayer): Boolean {
        playerDao?.delete(player)

        return true
    }
}