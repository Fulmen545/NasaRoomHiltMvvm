package com.fulmen.nasaroom.data.repository

import com.fulmen.nasaroom.data.api.ApiDataSource
import com.fulmen.nasaroom.data.db.ItemDao
import com.fulmen.nasaroom.util.performDbOperation
import com.senacor.nasamvvm.data.model.ItemData
import javax.inject.Inject

class NasaRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val itemDao: ItemDao
) {
    suspend fun getNasaData() = apiDataSource.getNasaData()

    fun getFavorites() = performDbOperation(databaseQuery = { itemDao.getAllFavorites() })

    suspend fun deleteFavorite(link: String) = itemDao.deleteFavorite(link)

    suspend fun insertFavorite(itemData: ItemData) = itemDao.insert(itemData)

    fun isFavorite(link: String) = performDbOperation(databaseQuery = { itemDao.isFavorite(link) })
}