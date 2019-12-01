package com.anibalbastias.android.cutepaws.presentation.ui.breedDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.module.getViewModel
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.databinding.FragmentFullImageBinding
import com.anibalbastias.android.cutepaws.presentation.appComponent
import com.anibalbastias.android.cutepaws.presentation.getAppContext

/**
 * Created by anibalbastias on 2019-09-09.
 */

class FullImageFragment : BaseModuleFragment() {

    private var binding: FragmentFullImageBinding? = null

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_full_image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        setHasOptionsMenu(false)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFullImageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tdSharedViewModel = sharedViewModel
        binding?.zoomBackButton?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}