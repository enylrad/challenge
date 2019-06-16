package com.company.app.ui.bindadapter

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.company.app.commons.custom.SpinnerExtensions
import com.company.app.commons.custom.SpinnerExtensions.setSpinnerEntries
import com.company.app.commons.custom.SpinnerExtensions.setSpinnerItemSelectedListener
import com.company.app.commons.custom.SpinnerExtensions.setSpinnerValue

class SpinnerBindings {

    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<Any>?) {
        setSpinnerEntries(entries)
    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
        setSpinnerItemSelectedListener(itemSelectedListener)
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        setSpinnerValue(newValue)
    }
}