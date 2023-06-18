package com.gostev.pizza_example.repository

import com.gostev.pizza_example.R
import com.gostev.pizza_example.api.PizzaApi
import com.gostev.pizza_example.data.Category
import com.gostev.pizza_example.db.CategoryDao
import com.gostev.pizza_example.ui.data.BannerViewItem
import javax.inject.Inject


class PizzaRepository @Inject constructor(
    private val pizzaApi: PizzaApi,
    private val categoryDao: CategoryDao
) {
    suspend fun getCategory(): List<Category>? {
        val categories = pizzaApi.getAll()
        categories?.let {
            it.sortedBy { it1 -> it1.id_category }
            setCategoriesToDb(it)
        }

        return categories
    }

    suspend fun getBanners(): List<BannerViewItem> {
        val banners = arrayListOf<BannerViewItem>()
        banners.add(BannerViewItem(R.drawable.image1))
        banners.add(BannerViewItem(R.drawable.image1))
        banners.add(BannerViewItem(R.drawable.image1))
        banners.add(BannerViewItem(R.drawable.image1))

        return banners
    }

    fun getCategoryFromDb() = categoryDao.getAll().sortedBy { it.id_category }

    private suspend fun setCategoriesToDb(categories: List<Category>) {
        categoryDao.deleteTable()
        categoryDao.insert(categories)
    }
}