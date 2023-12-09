package com.example.exercise04

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

private val positions = arrayOf("Striker", "Middle fielder", "Defender", "Goalkeeper")

@Entity(tableName = "players")
class DBPlayer {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var playerName: String? = null
    var playerSurname: String? = null
    var playerDesc: String? = null
    var playerNumber: String? = null
    var playerPosition: String? = null
    var isGood: Boolean = false

    constructor()

    constructor(
        num: Int
    ) : this() {
        playerName = "Player $num"
        playerSurname = "Surname $num"
        playerDesc = "Default specification"
        playerNumber = Random.nextInt(1, 100).toString()
        playerPosition = positions[Random.nextInt(0, 4)]
        isGood = Random.nextBoolean()
    }

    constructor(
        playerName: String?,
        playerSurname: String?,
        playerDesc: String?,
        playerNumber: String?,
        playerPosition: String?,
        isGood: Boolean
    ) : this() {
        this.playerName = playerName
        this.playerSurname = playerSurname
        this.playerDesc = playerDesc
        this.playerNumber = playerNumber
        this.playerPosition = playerPosition
        this.isGood = isGood
    }

    constructor(
        id: Int,
        playerName: String?,
        playerSurname: String?,
        playerDesc: String?,
        playerNumber: String?,
        playerPosition: String?,
        isGood: Boolean
    ) : this() {
        this.id = id
        this.playerName = playerName
        this.playerSurname = playerSurname
        this.playerDesc = playerDesc
        this.playerNumber = playerNumber
        this.playerPosition = playerPosition
        this.isGood = isGood
    }
}