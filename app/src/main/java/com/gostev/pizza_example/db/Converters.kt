package com.gostev.pizza_example.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.gostev.pizza_example.data.Pizza


class Converters {
    @TypeConverter
    fun listToJson(value: List<Pizza>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Pizza>::class.java).toList()
}