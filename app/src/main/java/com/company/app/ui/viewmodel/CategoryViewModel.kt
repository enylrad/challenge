package com.company.app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.app.commons.data.Resource
import com.company.app.commons.data.local.entity.CategoryEntity
import com.company.app.commons.data.local.remote.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/*
 * We are injecting the MovieDao class
 * and the MovieApiService class to the ViewModel.
 * */
class CategoryViewModel @Inject constructor(
        private val apiService: ApiService
) : ViewModel() {

    private val categoryListLiveData = MutableLiveData<Resource<List<CategoryEntity>>>()

    private val subCategoryListLiveData = MutableLiveData<Resource<List<CategoryEntity>>>()

    fun getCategories() {
        apiService.getCategories().subscribeOn(Schedulers.io())
                .flatMap { categories ->
                    Observable.just(
                            Resource.success(categories)
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { resource ->
                            if (resource.isSuccess) {
                                getCategoryListLiveData().postValue(resource)
                            }
                        },
                        onError = { throwable ->
                            getCategoryListLiveData().postValue(Resource.error(throwable.localizedMessage, listOf()))
                        }
                )
    }

    fun getSubCategories(idCategory: String) {
        apiService.getSubCategories(idCategory).subscribeOn(Schedulers.io())
                .flatMap { categories ->
                    Observable.just(
                            Resource.success(categories)
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { resource ->
                            if (resource.isSuccess) {
                                getSubCategoryListLiveData().postValue(resource)
                            }
                        },
                        onError = { throwable ->
                            getSubCategoryListLiveData().postValue(Resource.error(throwable.localizedMessage, listOf()))
                        }
                )
    }


    fun getCategoryListLiveData() = categoryListLiveData

    fun getSubCategoryListLiveData() = subCategoryListLiveData
}
