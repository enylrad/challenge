package com.company.app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.app.commons.data.Resource
import com.company.app.commons.data.local.entity.RequestEntity
import com.company.app.core.application.SessionPrefs
import javax.inject.Inject

class RequestViewModel @Inject constructor(
        val sessionPrefs: SessionPrefs
) : ViewModel() {

    private val requestListLiveData = MutableLiveData<Resource<MutableList<RequestEntity>>>()

    fun getRequests() {
        requestListLiveData.postValue(Resource.success(sessionPrefs.getRequestLists()))
    }

    fun addRequest(request: RequestEntity) {
        val requestList = sessionPrefs.getRequestLists()
        requestList.add(request)
        sessionPrefs.saveRequestLists(requestList)
        requestListLiveData.postValue(Resource.success(requestList))
    }

    fun getRequestListLiveData() = requestListLiveData
}
