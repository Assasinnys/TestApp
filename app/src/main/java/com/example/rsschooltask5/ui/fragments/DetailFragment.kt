package com.example.rsschooltask5.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.rsschooltask5.R
import com.example.rsschooltask5.util.daggerAppComponent
import com.example.rsschooltask5.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailFragmentViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerAppComponent().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()

        makeViewGoneAnim(btn_save_to_gallery, 3000)

        iv_cat_photo.setOnClickListener {
            if (btn_save_to_gallery.visibility == View.GONE) makeViewVisibleAnim(btn_save_to_gallery)
            else makeViewGoneAnim(btn_save_to_gallery)
        }

        btn_save_to_gallery.setOnClickListener {
            viewModel.notifySavePhotoClicked()
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
}
