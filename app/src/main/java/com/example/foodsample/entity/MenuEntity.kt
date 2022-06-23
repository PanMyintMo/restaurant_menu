package com.example.foodsample.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodsample.models.Menu
import java.text.DateFormat
import java.util.*


@Entity(tableName = "menu_table")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, val name: String = "", val price: Double = 0.0,val time:String ,val paid :Boolean)

