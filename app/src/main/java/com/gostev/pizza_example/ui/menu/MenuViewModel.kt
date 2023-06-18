package com.gostev.pizza_example.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gostev.pizza_example.data.Category
import com.gostev.pizza_example.repository.PizzaRepository
import com.gostev.pizza_example.ui.data.BannerViewItem
import com.gostev.pizza_example.ui.data.CategoryViewItem
import com.gostev.pizza_example.ui.data.PizzaViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(private val repository: PizzaRepository) : ViewModel() {

    val banners = MutableLiveData<List<BannerViewItem>>()
    val category = MutableLiveData<List<CategoryViewItem>>()
    val pizza = MutableLiveData<List<PizzaViewItem>>()
    val error = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        error.postValue(throwable.localizedMessage)
        getCategoryFromDb()
    }

    init {
        getCategory()
    }

    fun getBanners() {
        viewModelScope.launch(exceptionHandler) {
            banners.postValue(repository.getBanners())
        }
    }

    private fun getCategory() {
        viewModelScope.launch(exceptionHandler) {
            val categoryList = repository.getCategory()
            categoryList?.let { mapCategory(it) }
        }
    }

    private fun getCategoryFromDb() {
        viewModelScope.launch(Dispatchers.IO) {

            val categoryList = repository.getCategoryFromDb()
            mapCategory(categoryList)
        }
    }

    private fun mapCategory(categoryList: List<Category>) {
        val categoryViewItemList = arrayListOf<CategoryViewItem>()
        val pizzaViewItemList = arrayListOf<PizzaViewItem>()

        for ((index, element) in categoryList.withIndex()) {
            val categoryViewItem = CategoryViewItem(
                name = element.name,
                startItemsIndex = pizzaViewItemList.size
            )
            categoryViewItemList.add(categoryViewItem)

            element.items.forEach {
                val pizzaViewItem = PizzaViewItem(
                    name = it.name,
                    description = it.description,
                    photo = it.image_url,
                    price = it.price,
                    categoryIndex = index
                )
                pizzaViewItemList.add(pizzaViewItem)
            }
        }
        category.postValue(categoryViewItemList)
        pizza.postValue(pizzaViewItemList)
    }
}