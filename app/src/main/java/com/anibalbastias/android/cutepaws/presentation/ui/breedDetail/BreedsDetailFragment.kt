package com.anibalbastias.android.cutepaws.presentation.ui.breedDetail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.transition.TransitionInflater
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.module.getViewModel
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.databinding.FragmentBreedsDetailBinding
import com.anibalbastias.android.cutepaws.presentation.appComponent
import com.anibalbastias.android.cutepaws.presentation.getAppContext
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsItemViewData
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsViewData
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.viewmodel.BreedsViewModel
import com.anibalbastias.android.cutepaws.presentation.util.*
import com.anibalbastias.android.cutepaws.presentation.util.adapter.base.BaseBindClickHandler

/**
 * Created by anibalbastias on 2019-11-25.
 */

class BreedsDetailFragment : BaseModuleFragment(), BaseBindClickHandler<CutePawsItemViewData> {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_breeds_detail

    private lateinit var binding: FragmentBreedsDetailBinding
    private lateinit var breedsViewModel: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }

        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
        breedsViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavController(this@BreedsDetailFragment.view)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentBreedsDetailBinding
        binding.cutePawsViewModel = breedsViewModel
        binding.cutePawsItemListener = this
        binding.sharedViewModel = sharedViewModel
        binding.lifecycleOwner = this

        initToolbar()
        initViewModel()
        fetchImagesByBreed()
    }

    private fun fetchImagesByBreed() {
        breedsViewModel.apply {
            getDogImagesByBreedLiveData().value?.data?.let {
                setBreedsData(it)
            } ?: run {
                isLoading.set(true)
                sharedViewModel.breedItemViewData.breed?.let { getDogImagesByBreed(it.toLowerCase()) }
            }
        }
    }

    private fun setBreedsData(viewData: CutePawsViewData?) {
        breedsViewModel.apply {

            // Notify observers
            isLoading.set(false)
            isError.set(false)

            // Set data
            val breedList = arrayListOf<CutePawsItemViewData>()
            viewData?.list?.forEach {
                breedList.add(
                    CutePawsItemViewData(
                        breed = String.empty(),
                        imageUrl = ObservableField(it)
                    )
                )
            }
            cutePawsList.set(breedList)
        }
    }

    private fun initViewModel() {
        // Fetch Dog Images by Breeds
        implementObserver(breedsViewModel.getDogImagesByBreedLiveData(),
            successBlock = { viewData -> setBreedsData(viewData) },
            errorBlock = { showErrorView() })
    }

    private fun showErrorView() {

    }

    private fun initToolbar() {
        binding.toolbar?.run {
            title = sharedViewModel.breedItemViewData.breed
            applyFontForToolbarTitle(activity!!)
            setArrowUpToolbar(activity!!)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onClickView(view: View, item: CutePawsItemViewData) {

    }
}