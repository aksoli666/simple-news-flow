package com.example.simplenewsflow.design

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenewsflow.data.api.NewsRepo
import com.example.simplenewsflow.model.ByCategoryResponse
import com.example.simplenewsflow.model.KeyWordResponse
import com.example.simplenewsflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: NewsRepo): ViewModel() {
    val newsKeywordData: MutableLiveData<Resource<KeyWordResponse>> = MutableLiveData()
    val newsCategoryData: MutableLiveData<Resource<ByCategoryResponse>> = MutableLiveData()

    init {
        searchNewsByKeyword("bitcoin")
    }

    private fun searchNewsByKeyword(q: String) =
        viewModelScope.launch {
            newsKeywordData.postValue(Resource.Loading())
            val response = repo.searchNewsByKeyword(q = q)
            if (response.isSuccessful) {
                response.body().let { res ->
                    newsKeywordData.postValue(Resource.Success(res))
                }
            } else {
                newsKeywordData.postValue(Resource.Error(msg = response.message()))
            }
        }
}