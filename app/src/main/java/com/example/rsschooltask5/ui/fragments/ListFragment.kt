package com.example.rsschooltask5.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsschooltask5.R
import com.example.rsschooltask5.adapter.CatListAdapter
import com.example.rsschooltask5.util.PaginationListener
import com.example.rsschooltask5.util.catsAdapter
import com.example.rsschooltask5.viewmodel.ListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: ListFragmentViewModel by viewModels()
    private val paginationListener = PaginationListener { viewModel.notifyRecyclerEnd() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        bindViewModel()
    }

    private fun initRecyclerView() {
        rv_cats.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_cats.adapter = CatListAdapter {
            findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }
        rv_cats.addOnScrollListener(paginationListener)
    }

    private fun bindViewModel() {
        viewModel.getCatList().observe(viewLifecycleOwner) {
//            catsAdapter().removeLoadingView()
            catsAdapter().setCats(it)
        }
        viewModel.isLoading().observe(viewLifecycleOwner) {
            if (it) pb_loading.show()
            else pb_loading.hide()
        }
        viewModel.getNextPageLoading().observe(viewLifecycleOwner) { isPageLoading ->
            if (isPageLoading) {
                catsAdapter().addLoadingView()
            } else {
                paginationListener.loadingFinished()
            }
        }
    }
}