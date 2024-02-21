package com.umc.ttoklip.presentation.search2.fragment

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentItemSearchBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.mypage.SortSpinnerAdapter
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.adapter.SearchRVA
import com.umc.ttoklip.presentation.search2.SearchViewModel2
import com.umc.ttoklip.presentation.search2.SearchViewModelImpl2
import kotlinx.coroutines.launch


class SearchNewsFragment() :
    BaseFragment<FragmentItemSearchBinding>(R.layout.fragment_item_search) {

    private val viewModel: SearchViewModel2 by activityViewModels<SearchViewModelImpl2>()

    private val searchRVA by lazy {
        SearchRVA { category, id ->
            when (category) {
                1 -> {
                    startActivity(ArticleActivity.newIntent(requireContext(), id))
                }
                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
                else -> {}
            }
        }
    }

    private val spinnerA by lazy {
        val sortFilters = listOf(
            "최신순",
            "인기순",
        )
        SortSpinnerAdapter(requireContext(), sortFilters)
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchNewsList.collect{
                    searchRVA.submitList(it)
                }
            }
        }
    }

    override fun initView() {
        binding.filterSpinner.adapter = spinnerA
        binding.filterSpinner.setSelection(0)
        Log.d("왜안","${spinnerA.item}")

        binding.filterSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.reset(1)
                Log.d("왜안","${spinnerA.item}")
                if (binding.filterSpinner.selectedItem == "최신순"){
                    viewModel.getNewsSearch("latest")
                } else{
                    viewModel.getNewsSearch("popularity")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.communicationRv.adapter = searchRVA
        binding.communicationRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalItemViewCount = recyclerView.adapter!!.itemCount - 1

                if (newState == 2 && !recyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == totalItemViewCount
                ) {
                    if (binding.filterSpinner.selectedItem == "최신순"){
                        viewModel.getNewsSearch("latest")
                    } else{
                        viewModel.getNewsSearch("popularity")
                    }
                }
            }

        })
    }
}