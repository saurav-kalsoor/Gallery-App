package com.example.galleryapp.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.galleryapp.R
import com.example.galleryapp.database.ImageDatabase
import com.example.galleryapp.databinding.FragmentViewPagerBinding
import com.example.galleryapp.imageDisplay.ImageDisplayViewModel
import com.example.galleryapp.imageDisplay.ImageDisplayViewModelProvider

class ViewPagerFragment : DialogFragment() {

    private lateinit var binding: FragmentViewPagerBinding

    var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ImageDatabase.getInstance(application).imageDatabaseDao

        val viewModelFactory = ImageDisplayViewModelProvider(dataSource, application)

        val imageDisplayViewModel = ViewModelProvider(this, viewModelFactory).get(ImageDisplayViewModel::class.java)

        val viewPagerAdapter = ViewPagerAdapter(application)

        binding.viewPagerMain.adapter = viewPagerAdapter

        val args = ViewPagerFragmentArgs.fromBundle(requireArguments())

        currentPosition = args.position

        imageDisplayViewModel.imagesList.observe(viewLifecycleOwner, {
            viewPagerAdapter.imagesList = it
            binding.viewPagerMain.setCurrentItem(currentPosition, false)
        })



        return binding.root
    }


}