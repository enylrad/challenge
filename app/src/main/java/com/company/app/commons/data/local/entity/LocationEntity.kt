package com.company.app.commons.data.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationEntity(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("parent_id")
    var parentId: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("zip")
    var zip: String? = null,

    @SerializedName("geo_lat")
    var latitude: Double? = null,

    @SerializedName("geo_lng")
    var longitude: Double? = null,

    @SerializedName("level")
    var level: Int? = null,

    @SerializedName("slug")
    var slug: String? = null

) : Parcelable