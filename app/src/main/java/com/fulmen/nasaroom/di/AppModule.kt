package com.fulmen.nasaroom.di

import android.content.Context
import com.fulmen.nasaroom.data.api.ApiDataSource
import com.fulmen.nasaroom.data.api.ApiService
import com.fulmen.nasaroom.data.db.AppDatabase
import com.fulmen.nasaroom.data.db.ItemDao
import com.fulmen.nasaroom.data.repository.NasaRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val BASE_URL = "https://api.rss2json.com/v1/"

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiDataSource(apiService: ApiService) = ApiDataSource(apiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideItemDao(db: AppDatabase) = db.itemDao()

    @Singleton
    @Provides
    fun provideRepository(apiDataSource: ApiDataSource, itemDao: ItemDao) = NasaRepository(apiDataSource, itemDao)

}