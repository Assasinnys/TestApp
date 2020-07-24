package com.example.rsschooltask5.util

import androidx.fragment.app.Fragment
import com.example.rsschooltask5.App
import com.example.rsschooltask5.adapter.CatListAdapter
import com.example.rsschooltask5.ui.fragments.ListFragment
import kotlinx.android.synthetic.main.fragment_list.*

fun ListFragment.catsAdapter() = rv_cats.adapter as CatListAdapter

fun Fragment.daggerAppComponent() = (requireActivity().application as App).appComponent
