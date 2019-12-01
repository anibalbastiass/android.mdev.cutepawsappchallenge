package com.anibalbastias.android.cutepaws.presentation.ui.breeds

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.module.getViewModel
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.base.view.ResourceState
import com.anibalbastias.android.cutepaws.databinding.FragmentBreedsListBinding
import com.anibalbastias.android.cutepaws.presentation.appComponent
import com.anibalbastias.android.cutepaws.presentation.getAppContext
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsItemViewData
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsViewData
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.viewmodel.BreedsViewModel
import com.anibalbastias.android.cutepaws.presentation.util.adapter.base.BaseBindClickHandler
import com.anibalbastias.android.cutepaws.presentation.util.applyFontForToolbarTitle
import com.anibalbastias.android.cutepaws.presentation.util.implementObserver
import com.anibalbastias.android.cutepaws.presentation.util.setNoArrowUpToolbar
import com.anibalbastias.android.cutepaws.presentation.util.toast

/**
 * Created by anibalbastias on 2019-11-25.
 */

class BreedsListFragment : BaseModuleFragment(), BaseBindClickHandler<CutePawsItemViewData> {

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
        binding.cutePawsItemListener = this
        binding.lifecycleOwner = this

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

    private fun setBreedsData(viewData: CutePawsViewData?) {
        breedsViewModel.apply {
            isLoading.set(false)
            isError.set(false)
            binding.cutePawsListSwipeRefreshLayout?.isRefreshing = false
            cutePawsList.set(viewData?.breedList)

            // Set Images for each breed
            viewData?.breedList?.forEach {
                getRandomImageBreed(it.breed?.toLowerCase()!!)
            }
        }
    }

    private fun setImageBreedsData(viewData: CutePawsViewData?) {
        breedsViewModel.isError.set(false)
        breedsViewModel?.cutePawsList.get()?.forEach {
            if (viewData?.disclaimer?.contains(it.breed!!, ignoreCase = true) == true)
                it.imageUrl?.set(viewData.disclaimer)
        }
    }

    override fun onClickView(view: View, item: CutePawsItemViewData) {
        activity?.toast(item.breed)
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
            errorBlock = { showErrorView() })

        // Fetch Image Breeds
        breedsViewModel.getBreedsImageLiveData().observeForever {
            handleLiveData(it.status, it.data, it.message)
        }
    }

    private fun handleLiveData(status: ResourceState, data: CutePawsViewData?, message: String?) {
        when (status) {
            ResourceState.SUCCESS -> setImageBreedsData(data)
            else -> {
            }
        }
    }

    private fun showErrorView() {
        breedsViewModel.isError.set(true)
    }
}