package com.example.rsschooltask5.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsschooltask5.R
import com.example.rsschooltask5.adapter.CatListAdapter
import com.example.rsschooltask5.util.CAT_KEY_BUNDLE
import com.example.rsschooltask5.util.PaginationListener
import com.example.rsschooltask5.util.catsAdapter
import com.example.rsschooltask5.util.daggerAppComponent
import com.example.rsschooltask5.viewmodel.ListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ListFragmentViewModel by viewModels { viewModelFactory }
    private val paginationListener = PaginationListener { viewModel.notifyRecyclerEnd() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        bindViewModel()
    }

    private fun initRecyclerView() {
        rv_cats.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_cats.adapter = CatListAdapter {
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundleOf(
                CAT_KEY_BUNDLE to it?.imageUrl))
        }
        rv_cats.addOnScrollListener(paginationListener)
    }

    private fun bindViewModel() {
        viewModel.getCatList().observe(viewLifecycleOwner) {
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
