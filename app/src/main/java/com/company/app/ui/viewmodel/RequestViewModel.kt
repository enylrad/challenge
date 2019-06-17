package com.company.app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.app.commons.data.Resource
import com.company.app.commons.data.local.entity.RequestEntity
import javax.inject.Inject

class RequestViewModel @Inject constructor() : ViewModel() {

    private val requestListLiveData = MutableLiveData<Resource<MutableList<RequestEntity>>>()

    fun getRequests() {
        requestListLiveData.postValue(Resource.success(requestListLiveData.value?.data
                ?: mutableListOf()))
    }

    fun addRequest(request: RequestEntity) {
        val list = requestListLiveData.value?.data ?: mutableListOf()
        list.add(request)
        requestListLiveData.postValue(Resource.success(list))
    }

    fun getRequestListLiveData() = requestListLiveData
}
