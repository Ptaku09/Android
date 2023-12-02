package com.example.exercise04

import kotlin.random.Random

class DataItem {
    private val positions = arrayOf("Striker", "Middle fielder", "Defender", "Goalkeeper")

    var playerName: String = ""
    var playerSurname: String = ""
    var playerDesc: String = "Default specification"
    var playerNumber: Int = Random.nextInt(1, 100)
    var playerPosition: String = positions[Random.nextInt(0, 4)]
    var isGood: Boolean = Random.nextBoolean()

    constructor()

    constructor(
        name: String,
        surname: String,
        desc: String,
        number: Int,
        position: String,
        good: Boolean
    ) : this() {
        playerName = name
        playerSurname = surname
        playerDesc = desc
        playerNumber = number
        playerPosition = position
        isGood = good
    }
}




