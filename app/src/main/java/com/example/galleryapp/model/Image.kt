package com.example.galleryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class Image(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    var imageId : Long = 0L,

    @ColumnInfo(name = "time_stamp")
    var timeStamp: Long = 0L,

    @ColumnInfo(name = "image_url")
    var imageUrl : String = "NIL"
)