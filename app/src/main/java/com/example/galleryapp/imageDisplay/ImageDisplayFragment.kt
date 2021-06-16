package com.example.galleryapp.imageDisplay

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.bitMapToString
import com.example.galleryapp.database.ImageDatabase
import com.example.galleryapp.databinding.FragmentDisplayImageBinding
import com.example.galleryapp.model.Image


class ImageDisplayFragment : Fragment(), ItemClickListener {

    lateinit var imageDisplayViewModel: ImageDisplayViewModel
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val inputStream = result.data?.data?.let { activity?.applicationContext?.contentResolver?.openInputStream(it) }
            val bitmap = BitmapFactory.decodeStream(inputStream)
            addImage(bitmap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding : FragmentDisplayImageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_display_image, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ImageDatabase.getInstance(application).imageDatabaseDao

        val viewModelFactory = ImageDisplayViewModelProvider(dataSource, application)

        imageDisplayViewModel = ViewModelProvider(this, viewModelFactory).get(ImageDisplayViewModel::class.java)

        binding.imageDisplayViewModel = imageDisplayViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = GalleryAdapter(this)

        binding.recyclerViewImages.adapter = adapter
        binding.recyclerViewImages.layoutManager = GridLayoutManager(context, 3)

        imageDisplayViewModel.imagesList.observe( viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.buttonAddImage.setOnClickListener {
            pickImage()
        }

        return binding.root
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private fun addImage(bitmap: Bitmap) {

        val imgUrl = bitMapToString(bitmap)
        val image = Image()
        imgUrl?.let {
            image.imageUrl = imgUrl
        }
        imageDisplayViewModel.addImage(image)
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(ImageDisplayFragmentDirections.actionImageDisplayFragmentToViewPagerFragment(position))
    }

    override fun onDelete(imageId: Long) {
        imageDisplayViewModel.delete(imageId)
    }

}