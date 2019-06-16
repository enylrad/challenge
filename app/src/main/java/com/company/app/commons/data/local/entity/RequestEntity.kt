package com.company.app.commons.data.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestEntity(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("phone")
    var phone: String? = null,

    @SerializedName("sub_category_id")
    var subCategoryId: String? = null,

    @SerializedName("geo_lat")
    var location: Int? = null
) : Parcelable