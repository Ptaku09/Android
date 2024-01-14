package com.example.project.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

val positions = arrayOf("Striker", "Middle Fielder", "Defender", "Goalkeeper")

@Entity(tableName = "player_table")
class PlayerData {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "name")
    var text_name: String = "Default player name"

    @ColumnInfo(name = "surname")
    var text_surname: String = "Default player surname"

    @ColumnInfo(name = "number")
    var item_number: Int = Random.nextInt(0, 100)

    @ColumnInfo(name = "position")
    var item_position: String = positions[Random.nextInt(0, 3)]

    @ColumnInfo(name = "is_good")
    var good: Boolean = Random.nextBoolean()

    @ColumnInfo(name = "photo_uri")
    var photo_uri: String = ""

    constructor()

    constructor(num: Int) : this() {
        text_name = "Default player name $num"
    }

    constructor(
        name: String,
        surname: String,
        number: Int,
        position: String,
        isGood: Boolean,
    ) : this() {
        text_name = name
        text_surname = surname
        item_number = number
        item_position = position
        good = isGood
    }

    override fun toString(): String {
        return "PlayerData(id=$id, text_name='$text_name', text_surname='$text_surname', number=$item_number, position='$item_position', good=$good, photo_uri='$photo_uri')"
    }
}




