package com.example.simplenewsflow.design.category

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
import com.example.simplenewsflow.databinding.FragmentCategoryBinding
import com.example.simplenewsflow.design.adapter.NewsByCategoryAdapter
import com.example.simplenewsflow.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<CategoryViewModel>()
    lateinit var newsByCategoryAdapter: NewsByCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupSearch()
        setupMainNav()
    }

    private fun setupSearch() {
        var job: Job? = null
        mBinding.categorySearch.addTextChangedListener {text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(100L)
                text?.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.searchNewsByCategory(category = it.toString())
                    }
                }
            }
        }
        viewModel.categoryNewsLiveDate.observe(viewLifecycleOwner) {responce ->
            when(responce) {
                is Resource.Success -> {
                    mBinding.pagCategoryProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsByCategoryAdapter.differ.submitList(it.sources)
                    }
                }
                is Resource.Error -> {
                    mBinding.pagCategoryProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("checkData", "CategoryFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    mBinding.pagCategoryProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupMainNav() {
        mBinding.toMainIcon.setOnClickListener {
            findNavController().navigate(R.id
                .action_categoryFragment_to_mainFragment)
        }
    }

    private fun initAdapter() {
        newsByCategoryAdapter = NewsByCategoryAdapter()
        mBinding.searchNewsAdapter.apply {
            adapter = newsByCategoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}