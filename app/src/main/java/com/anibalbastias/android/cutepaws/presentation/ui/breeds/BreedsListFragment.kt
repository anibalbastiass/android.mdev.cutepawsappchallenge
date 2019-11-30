package com.anibalbastias.android.cutepaws.presentation.ui.breeds

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.module.getViewModel
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.databinding.FragmentBreedsListBinding
import com.anibalbastias.android.cutepaws.presentation.appComponent
import com.anibalbastias.android.cutepaws.presentation.getAppContext
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsViewData
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.viewmodel.BreedsViewModel
import com.anibalbastias.android.cutepaws.presentation.util.*

/**
 * Created by anibalbastias on 2019-11-25.
 */

class BreedsListFragment : BaseModuleFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_breeds_list

    private lateinit var binding: FragmentBreedsListBinding
    private lateinit var breedsViewModel: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
        breedsViewModel = getViewModel(viewModelFactory)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavController(this@BreedsListFragment.view)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentBreedsListBinding
        binding.cutePawsViewModel = breedsViewModel
        //binding.lifecycleOwner = this

        initToolbar()
        initViewModel()
        fetchBreeds()
    }

    private fun fetchBreeds() {
        breedsViewModel.apply {
            getBreedsLiveData().value?.data?.let {
                setBreedsData(it)
            } ?: run {
                isLoading.set(true)
                getSearchSongsResultsData()
            }
        }
    }

    private fun setBreedsData(it: CutePawsViewData?) {
        it
    }

    private fun initToolbar() {
        binding.cutePawsToolbar?.run {
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
        }
    }

    private fun initViewModel() {
        // Fetch Breeds
        implementObserver(breedsViewModel.getBreedsLiveData(),
            successBlock = { viewData -> setBreedsData(viewData) },
            loadingBlock = { showLoadingView() },
            errorBlock = { showErrorView(it) })
    }

    private fun showErrorView(errorMessage: String?) {

    }

    private fun showLoadingView() {

    }
}