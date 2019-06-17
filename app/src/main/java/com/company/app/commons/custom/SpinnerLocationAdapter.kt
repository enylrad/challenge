package com.company.app.commons.custom

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.company.app.R
import com.company.app.commons.data.local.entity.LocationEntity
import com.company.app.core.utils.stripAccents


class SpinnerLocationAdapter(context: Context, values: List<LocationEntity>) :
        ArrayAdapter<LocationEntity>(context, R.layout.adapter_spinner_custom, values) {
    private val items: List<LocationEntity> = values
    private val tempItems: List<LocationEntity> = values.toList()
    private val suggestions: MutableList<LocationEntity> = mutableListOf()
    private val locationEntityFilter = object : Filter() {

        override fun convertResultToString(resultValue: Any): CharSequence {
            val locationEntity = resultValue as LocationEntity
            return locationEntity.name.toString()
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            return if (constraint != null) {
                suggestions.clear()
                for (location in tempItems) {
                    val nameCheck = checkIfStartWithName(location, constraint)
                    val zipCheck = checkIfStartWithZip(location, constraint)
                    if (nameCheck || zipCheck) {
                        suggestions.add(location)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                filterResults
            } else {
                FilterResults()
            }
        }

        private fun checkIfStartWithName(location: LocationEntity, constraint: CharSequence): Boolean {
            val locationNameNormalized = location.name?.toLowerCase()?.stripAccents()
            val textUserNormalized = constraint.toString().toLowerCase().trim().stripAccents()
            return locationNameNormalized?.startsWith(textUserNormalized) == true
        }

        private fun checkIfStartWithZip(location: LocationEntity, constraint: CharSequence): Boolean {
            val locationZip = location.zip
            val textUserZipNormalized = constraint.toString().toLowerCase().trim()
            return locationZip?.startsWith(textUserZipNormalized) == true
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val tempValues = results?.values as MutableList<LocationEntity>?
            if (results != null && results.count > 0) {
                clear()
                for (LocationEntityObj in tempValues ?: mutableListOf()) {
                    add(LocationEntityObj)
                    notifyDataSetChanged()
                }
            } else {
                clear()
                notifyDataSetChanged()
            }
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        try {
            if (convertView == null) {
                val inflater = (context as Activity).layoutInflater
                view = inflater.inflate(R.layout.adapter_spinner_custom, parent, false)
            }
            val location = getItem(position)
            val name = view?.findViewById<View>(R.id.adapter_name) as TextView
            name.text = location?.name
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }

    override fun getItem(position: Int): LocationEntity? {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getFilter(): Filter {
        return locationEntityFilter
    }
}