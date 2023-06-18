package com.gostev.pizza_example.api

import com.gostev.pizza_example.data.Category
import retrofit2.http.GET


interface PizzaApi {
    @GET(".json")
   suspend fun getAll ():List<Category>?
}