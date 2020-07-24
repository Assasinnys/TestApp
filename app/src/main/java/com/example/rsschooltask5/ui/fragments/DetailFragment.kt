package com.example.rsschooltask5.ui.fragments

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.core.graphics.drawable.toBitmap
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
        bindViewModel()

        makeViewGoneAnim(btn_save_to_gallery, 3000)

        iv_cat_photo.setOnClickListener {
            if (btn_save_to_gallery.visibility == View.GONE) makeViewVisibleAnim(btn_save_to_gallery)
            else makeViewGoneAnim(btn_save_to_gallery)
        }

        btn_save_to_gallery.setOnClickListener {
            savePhoto()
            Toast.makeText(context, R.string.toast_photo_saved, Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeViewVisibleAnim(v: View, offset: Long = 0, duration: Long = 500) {
        v.visibility = View.VISIBLE
        v.animate()
            .alpha(1.0f)
            .setStartDelay(offset)
            .setDuration(duration)
            .start()
    }

    private fun makeViewGoneAnim(v: View, offset: Long = 0, duration: Long = 500) {
        btn_save_to_gallery.animate()
            .alpha(0.0f)
            .setDuration(duration)
            .setStartDelay(offset)
            .withEndAction { v.visibility = View.GONE }
            .start()
    }

    private fun bindViewModel() {
        viewModel.saveArgs(arguments)
        viewModel.getImageData().observe(viewLifecycleOwner) {
            iv_cat_photo.setImageDrawable(it)
        }
    }

    private fun savePhoto() {
        val contentResolver = requireActivity().contentResolver
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val cv = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "My_Awesome_Cat.png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }
        val uri = contentResolver.insert(imageCollection, cv)
        if (uri != null) {
            contentResolver.openOutputStream(uri).use {
                iv_cat_photo.drawable.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver.update(
                    uri,
                    contentValuesOf(MediaStore.Images.Media.IS_PENDING to 0),
                    null,
                    null
                )
            }
        }
    }
}
