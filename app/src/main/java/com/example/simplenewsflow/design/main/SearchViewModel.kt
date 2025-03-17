package com.example.simplenewsflow.design.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenewsflow.data.api.NewsRepo
import com.example.simplenewsflow.model.KeyWordResponse
import com.example.simplenewsflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepo): ViewModel() {
    val searchNewsLiveData: MutableLiveData<Resource<KeyWordResponse>> = MutableLiveData()

    init {
        searchNewsByKeyword("")
    }

    fun searchNewsByKeyword(query: String) =
        viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            val response = repository.searchNewsByKeyword(q = query)
            if (response.isSuccessful) {
                response.body().let { res ->
                    searchNewsLiveData.postValue(Resource.Success(res))
                }
            } else {
                searchNewsLiveData.postValue(Resource.Error(msg = response.message()))
            }
        }
}