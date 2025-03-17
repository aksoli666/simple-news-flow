package com.example.simplenewsflow.design.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenewsflow.data.api.NewsRepo
import com.example.simplenewsflow.model.ByCategoryResponse
import com.example.simplenewsflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: NewsRepo): ViewModel() {
    val categoryNewsLiveDate: MutableLiveData<Resource<ByCategoryResponse>> = MutableLiveData()

    init {
        searchNewsByCategory("general")
    }

    fun searchNewsByCategory(category: String) =
        viewModelScope.launch {
            categoryNewsLiveDate.postValue(Resource.Loading())
            val response = repository.searchNewsByCategory(category = category)
            if (response.isSuccessful) {
                response.body().let { res ->
                    categoryNewsLiveDate.postValue(Resource.Success(res))
                }
            } else {
                categoryNewsLiveDate.postValue(Resource.Error(msg = response.message()))
            }
        }
}