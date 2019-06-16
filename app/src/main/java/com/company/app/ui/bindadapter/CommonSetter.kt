package com.company.app.ui.bindadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.company.app.commons.data.local.entity.LocationEntity

@BindingAdapter(value = ["phone", "location"], requireAll = true)
fun bindPhoneLocation(view: TextView, phone: String, location: LocationEntity) {
    val text = "$phone (${location.name})"
    view.text = text
}