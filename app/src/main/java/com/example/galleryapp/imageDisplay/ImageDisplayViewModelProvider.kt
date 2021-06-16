package com.example.galleryapp.imageDisplay

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.galleryapp.database.ImageDatabaseDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ImageDisplayViewModelProvider(
    private val dataSource : ImageDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImageDisplayViewModel::class.java))
            return ImageDisplayViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}