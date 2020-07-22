package com.example.rsschooltask5.ui.fragments

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.rsschooltask5.R
import com.example.rsschooltask5.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailFragmentViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pb_loading.hide()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.saveArgs(arguments)
        viewModel.getImageData().observe(viewLifecycleOwner) {
            iv_cat_photo.setImageDrawable(it)
        }
    }

    private fun savePhoto(){
        val contentResolver = requireActivity().contentResolver
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val cv = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "My Awesome Cat")
        }
//        contentResolver.insert(imageCollection, )
    }
}