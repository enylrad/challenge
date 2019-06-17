package com.company.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.company.app.R
import com.company.app.commons.data.local.entity.RequestEntity
import com.company.app.core.constants.Extras
import com.company.app.databinding.FragmentRequestListBinding
import com.company.app.ui.adapter.RequestListAdapter
import com.company.app.ui.base.BaseFragment
import com.company.app.ui.viewmodel.RequestViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RequestListFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var requestViewModel: RequestViewModel

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
        requestViewModel = ViewModelProviders.of(this, viewModelFactory).get(RequestViewModel::class.java)
        requestViewModel.getRequestListLiveData().observe(this, Observer { resource ->
            if (resource.data?.isNotEmpty() == true) {
                binding.tvEmpty.visibility = View.GONE
                binding.adapter?.data = resource.data
            } else {
                binding.tvEmpty.visibility = View.VISIBLE
            }
        })
        arguments?.getParcelable<RequestEntity>(Extras.REQUEST_ADDED)?.let {
            requestViewModel.addRequest(it)
        } ?: requestViewModel.getRequests()
    }


    private fun initialiseView() {
        binding.btnAddRequest.setOnClickListener {
            findNavController().navigate(R.id.action_requestListFragment_to_requestCreateFragment)
        }
    }


}
