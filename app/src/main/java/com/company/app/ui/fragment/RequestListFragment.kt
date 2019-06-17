package com.company.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.company.app.R
import com.company.app.commons.data.local.entity.LocationEntity
import com.company.app.commons.data.local.entity.RequestEntity
import com.company.app.databinding.FragmentRequestListBinding
import com.company.app.ui.adapter.RequestListAdapter
import com.company.app.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection

class RequestListFragment : BaseFragment() {

    private lateinit var binding: FragmentRequestListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_list, container, false)
        val adapter = RequestListAdapter()
        binding.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseView()
    }

    private fun initViewModel() {

    }


    private fun initialiseView() {
        binding.btnAddRequest.setOnClickListener {
            findNavController().navigate(R.id.action_requestListFragment_to_requestCreateFragment)
        }
        val request = mutableListOf<RequestEntity>()
        for (x in 0..100) {
            request.add(
                    RequestEntity(
                            id = "$x",
                            name = "name $x",
                            email = "email $x",
                            description = "description $x",
                            phone = "phone $x",
                            location = LocationEntity(name = "location $x")
                    )
            )
        }
        binding.adapter?.data = request
    }


}
