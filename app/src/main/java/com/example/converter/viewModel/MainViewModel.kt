package com.example.converter.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.helper.Resource
import com.example.converter.model.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject public constructor(private val mainConverter: MainConverter) : ViewModel(){

    private val privateData = MutableLiveData<Resource<ApiResponse>>()
    val data  =  privateData
    val convertedRate = MutableLiveData<Double>()

    fun getConvertedData(access_key: String, from: String, to: String, amount: Double) {
        viewModelScope.launch {
            mainConverter.getConvertedData(access_key, from, to, amount).collect {
                data.value = it
            }
        }
    }
}