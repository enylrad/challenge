package com.company.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.company.app.R
import com.company.app.databinding.FragmentRequestListBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseView()
    }

    private fun initViewModel() {

    }


    private fun initialiseView() {

    }


}
