package com.gostev.pizza_example.di

import android.content.Context
import androidx.room.Room
import com.gostev.pizza_example.api.PizzaApi
import com.gostev.pizza_example.db.CategoryDB
import com.gostev.pizza_example.db.CategoryDao
import com.gostev.pizza_example.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun baseUrl() =BASE_URL

    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun oktHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logging())
        .connectTimeout(100, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl:String): PizzaApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(oktHttpClient())
            .build()
            .create(PizzaApi::class.java)

    @Provides
    @Singleton
    fun provideCategoryDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            CategoryDB::class.java,
            "category_db"
        ).build()

    @Provides
    fun provideCategoryDao(appDDataBase:CategoryDB) :CategoryDao{
        return appDDataBase.getCategoryDao()
    }
}