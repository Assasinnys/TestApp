package com.example.rsschooltask5.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import com.example.rsschooltask5.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pb_loading.show()
        Handler(Looper.getMainLooper()).postDelayed(3000) {
            pb_loading.hide()
            Toast.makeText(context, "Ready 3 sec", Toast.LENGTH_SHORT).show()
        }
    }
}