package com.example.converter.viewModel

import com.example.converter.network.ApiDataSource
import com.example.converter.network.BaseDataSource
import com.example.converter.model.ApiResponse
import com.example.converter.helper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainConverter @Inject constructor(private val apiDataSource: ApiDataSource) : BaseDataSource(){
    //Using coroutines flow to get the response from
    suspend fun getConvertedData(access_key: String, from: String, to: String, amount: Double): Flow<Resource<ApiResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getConvertedRate(access_key, from, to, amount) })
        }.flowOn(Dispatchers.IO)
    }
}