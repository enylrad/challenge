package com.company.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.app.commons.custom.AutoUpdatableAdapter
import com.company.app.commons.data.local.entity.RequestEntity
import com.company.app.databinding.RequestListItemBinding
import kotlin.properties.Delegates

class RequestListAdapter : RecyclerView.Adapter<RequestListAdapter.RequestViewHolder>(), AutoUpdatableAdapter {

    var data: List<RequestEntity> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n ->
            o.id == n.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RequestListItemBinding.inflate(layoutInflater, parent, false)
        return RequestViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getItem(position: Int): RequestEntity {
        return data[position]
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class RequestViewHolder(val binding: RequestListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bindTo(request: RequestEntity) {
            binding.request = request
            binding.executePendingBindings()
        }
    }
}
