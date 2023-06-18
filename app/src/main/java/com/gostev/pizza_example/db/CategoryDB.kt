package com.gostev.pizza_example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gostev.pizza_example.data.Category


@Database(entities = [Category::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class CategoryDB : RoomDatabase()  {
    abstract fun getCategoryDao(): CategoryDao
}