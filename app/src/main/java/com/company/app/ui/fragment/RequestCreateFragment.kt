package com.company.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.company.app.R
import com.company.app.commons.custom.SpinnerExtensions
import com.company.app.commons.custom.SpinnerExtensions.setSpinnerCategories
import com.company.app.commons.custom.SpinnerExtensions.setSpinnerItemSelectedListener
import com.company.app.commons.custom.SpinnerLocationAdapter
import com.company.app.commons.data.local.entity.CategoryEntity
import com.company.app.commons.data.local.entity.LocationEntity
import com.company.app.core.utils.isValidEmail
import com.company.app.databinding.FragmentRequestCreateBinding
import com.company.app.ui.base.BaseFragment
import com.company.app.ui.viewmodel.CategoryViewModel
import com.company.app.ui.viewmodel.LocationViewModel
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject


class RequestCreateFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var categoryViewModel: CategoryViewModel

    lateinit var locationViewModel: LocationViewModel

    private lateinit var binding: FragmentRequestCreateBinding

    private var locationSelected: LocationEntity? = null

    private var categorySelected: CategoryEntity? = null

    private var subCategorySelected: CategoryEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_create, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseView()
    }

    private fun initViewModel() {
        categoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)
        categoryViewModel.getCategoryListLiveData().observe(this, Observer { resource ->
            if (resource.data != null && resource.data.isNotEmpty()) {
                binding.spCategory.setSpinnerCategories(resource.data)
            } else {
                mainActivity()?.showErrorDialog(resource.message!!)
            }
        })
        categoryViewModel.getSubCategoryListLiveData().observe(this, Observer { resource ->
            if (resource.data != null && resource.data.isNotEmpty()) {
                binding.spSubCategory.setSpinnerCategories(resource.data)
            } else {
                mainActivity()?.showErrorDialog(resource.message!!)
            }
        })
        locationViewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel::class.java)
        locationViewModel.getLocationListLiveData().observe(this, Observer { resource ->
            if (resource.data != null && resource.data.isNotEmpty()) {
                val adapter = SpinnerLocationAdapter(binding.etLocation.context, resource.data)
                binding.etLocation.setAdapter(adapter)
            } else {
                mainActivity()?.showErrorDialog(resource.message!!)
            }
        })
        categoryViewModel.getCategories()
        locationViewModel.getLocations()
    }

    private fun initialiseView() {
        binding.spCategory.setSpinnerItemSelectedListener(object : SpinnerExtensions.ItemSelectedListener {
            override fun onItemSelected(item: Any) {
                val category = item as? CategoryEntity
                category?.let {
                    categorySelected = category
                    categoryViewModel.getSubCategories(category.id!!)
                }
            }
        })
        binding.spSubCategory.setSpinnerItemSelectedListener(object : SpinnerExtensions.ItemSelectedListener {
            override fun onItemSelected(item: Any) {
                val category = item as? CategoryEntity
                category?.let {
                    subCategorySelected = category
                }
            }
        })
        binding.spCategory.setOnTouchListener { _, _ ->
            mainActivity()?.hideSoftKeyboard()
            false
        }
        binding.spSubCategory.setOnTouchListener { _, _ ->
            mainActivity()?.hideSoftKeyboard()
            false
        }
        binding.parent.setOnClickListener {
            mainActivity()?.hideSoftKeyboard()
        }
        binding.etLocation.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val location = parent.adapter.getItem(position) as LocationEntity
            locationSelected = location
            binding.etLocation.setText(location.name)
        }
        binding.btnRequestBudged.setOnClickListener {
            if (checkData()) {
                //TODO
                mainActivity()?.onBackPressed()
            }
        }
    }

    private fun checkData(): Boolean {
        var valid = true
        if (binding.etName.text.toString().isEmpty()) {
            binding.etName.error = getString(R.string.name_required)
            valid = false
        }
        if (binding.etPhone.text.toString().isEmpty()) {
            binding.etPhone.error = getString(R.string.phone_required)
            valid = false
        } else {
            try {
                binding.etPhone.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                binding.etPhone.error = getString(R.string.error_format)
                valid = false
            }
        }

        if (binding.etMail.text.toString().isEmpty()) {
            binding.etMail.error = getString(R.string.mail_required)
            valid = false
        } else {
            if (!binding.etMail.text.toString().isValidEmail()) {
                binding.etMail.error = getString(R.string.error_format)
                valid = false
            }
        }

        if (locationSelected == null) {
            binding.etLocation.error = getString(R.string.location_required)
            valid = false
        }

        if (categorySelected == null) {
            Timber.w("No category selected")
            valid = false
        }
        if (subCategorySelected == null) {
            Timber.w("No subCategory selected")
            valid = false
        }

        return valid
    }


}
