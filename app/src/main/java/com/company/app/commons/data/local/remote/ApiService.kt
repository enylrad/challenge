package com.company.app.commons.data.local.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/category/list/")
    fun getCategories(): Observable<List<com.company.app.commons.data.local.entity.CategoryEntity>>

    @GET("/category/list/{id}")
    fun getSubCategories(@Path("id") idCategory: String): Observable<List<com.company.app.commons.data.local.entity.CategoryEntity>>

    @GET("/location/list")
    fun getLocations(): Observable<List<com.company.app.commons.data.local.entity.LocationEntity>>

}