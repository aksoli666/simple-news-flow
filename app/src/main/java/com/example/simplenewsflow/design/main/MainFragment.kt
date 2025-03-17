package com.example.simplenewsflow.design.main

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplenewsflow.R
import com.example.simplenewsflow.databinding.FragmentMainBinding
import com.example.simplenewsflow.design.adapter.NewsByKeywordAdapter
import com.example.simplenewsflow.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    lateinit var newsByKeywordAdapter: NewsByKeywordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupSearch()
        setupCategoryNav()
    }

    private fun setupSearch() {
        var job: Job? = null
        mBinding.edSearch.addTextChangedListener{text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(100L)
                text?.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.searchNewsByKeyword(query = it.toString())
                    }
                }
            }
        }
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    mBinding.pagSearchProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsByKeywordAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    mBinding.pagSearchProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    mBinding.pagSearchProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupCategoryNav() {
        mBinding.categoriesIcon.setOnClickListener {
            findNavController().navigate(R.id
                .action_mainFragment_to_categoryFragment)
        }
    }

    private fun initAdapter() {
        newsByKeywordAdapter = NewsByKeywordAdapter()
        mBinding.searchNewsAdapter.apply {
            adapter = newsByKeywordAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}