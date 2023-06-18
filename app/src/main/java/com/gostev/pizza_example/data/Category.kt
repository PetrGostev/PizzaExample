package com.gostev.pizza_example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gostev.pizza_example.db.Converters


@Entity(tableName = "category")
@TypeConverters(Converters::class)
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var id_category: Int,
    val name: String = "",
    val items: List<Pizza> = listOf()
)
