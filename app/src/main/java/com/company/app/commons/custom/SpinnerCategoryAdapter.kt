package com.company.app.commons.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.company.app.R
import com.company.app.commons.data.local.entity.CategoryEntity

class SpinnerCategoryAdapter(context: Context, val values: List<CategoryEntity>) : ArrayAdapter<CategoryEntity>(context, R.layout.adapter_spinner_custom, R.id.adapter_name, values) {
    override fun getCount(): Int = values.size

    override fun getItem(position: Int): CategoryEntity? = values[position]

    override fun getItemId(position: Int): Long = position.toLong()

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as View
        val textLocation = view.findViewById<TextView>(R.id.adapter_name)
        try {
            textLocation.text = values[position].name ?: ""
        } catch (ex: IndexOutOfBoundsException) {
        }

        return view
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    override fun getDropDownView(
            position: Int, convertView: View?,
            parent: ViewGroup
    ): View {
        val view = super.getView(position, convertView, parent) as View
        val textLocation = view.findViewById<TextView>(R.id.adapter_name)

        try {
            textLocation.text = values[position].name ?: ""
        } catch (ex: IndexOutOfBoundsException) {
        }
        return view
    }
}