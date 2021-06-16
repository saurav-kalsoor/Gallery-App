package com.example.galleryapp.imageDisplay

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.database.ImageDatabaseDao
import kotlinx.coroutines.launch
import com.example.galleryapp.model.Image

class ImageDisplayViewModel(
    dataSource : ImageDatabaseDao,
    application: Application) : ViewModel() {

    val database = dataSource

    val imagesList : LiveData<List<Image>> = database.getAllImages()

    fun addImage(image: Image){

        viewModelScope.launch {
            image.timeStamp = System.currentTimeMillis()
            database.insert(image)
        }
    }

    fun delete(imageId : Long){
        viewModelScope.launch {
            database.delete(imageId)
        }
    }
}