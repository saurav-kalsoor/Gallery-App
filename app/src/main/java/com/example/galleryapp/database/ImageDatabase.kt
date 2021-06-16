package com.example.galleryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.galleryapp.model.Image

@Database(entities = [Image::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {
    abstract val imageDatabaseDao : ImageDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE : ImageDatabase? = null

        fun getInstance(context : Context) : ImageDatabase{
            synchronized(this){

                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ImageDatabase::class.java,
                        "image_history_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                }
                return instance
            }
        }

    }
}