package com.senacor.nasamvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemData(
    @PrimaryKey
    val link: String,
    val title: String,
    val pubDate: String,
    val author: String,
    val thumbnail: String,
    val description: String,
    val favorite: Boolean = false
)
