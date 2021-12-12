package com.fulmen.nasaroom.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.senacor.nasamvvm.data.model.ItemData

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemData: ItemData)

    @Query("DELETE FROM items WHERE link = :link")
    suspend fun deleteFavorite(link: String)

    @Query("SELECT * FROM items")
    fun getAllFavorites(): LiveData<List<ItemData>>

    @Query("SELECT EXISTS(SELECT * FROM items WHERE link = :link)")
    fun isFavorite(link: String): LiveData<Boolean>
}