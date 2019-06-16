package com.company.app.commons.data.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryEntity(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("child_count")
    var childCount: Int? = null,

    @SerializedName("slug")
    var slug: String? = null

) : Parcelable