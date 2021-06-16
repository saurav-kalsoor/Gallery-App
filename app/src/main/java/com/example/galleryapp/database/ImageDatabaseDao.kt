package com.example.galleryapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.galleryapp.model.Image

@Dao
interface ImageDatabaseDao  {

    @Insert
    suspend fun insert(image : Image)

    @Query("SELECT * FROM image_table")
    fun getAllImages() : LiveData<List<Image>>

    @Query("DELETE FROM image_table WHERE image_id = :key")
    suspend fun delete(key : Long)
}