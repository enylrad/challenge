package com.company.app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.app.commons.data.Resource
import com.company.app.commons.data.local.entity.LocationEntity
import com.company.app.commons.data.local.remote.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationViewModel @Inject constructor(
        private val apiService: ApiService
) : ViewModel() {

    private val locationListLiveData = MutableLiveData<Resource<List<LocationEntity>>>()

    fun getLocations() {
        apiService.getLocations().subscribeOn(Schedulers.io())
                .flatMap { locations ->
                    Observable.just(
                            Resource.success(locations)
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { resource ->
                            if (resource.isSuccess) {
                                getLocationListLiveData().postValue(resource)
                            }
                        },
                        onError = { throwable ->
                            getLocationListLiveData().postValue(Resource.error(throwable.localizedMessage, listOf()))
                        }
                )
    }


    fun getLocationListLiveData() = locationListLiveData
}
