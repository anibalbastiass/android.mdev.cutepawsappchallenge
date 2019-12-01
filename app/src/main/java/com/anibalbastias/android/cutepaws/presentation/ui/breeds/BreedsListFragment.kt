package com.anibalbastias.android.cutepaws.presentation.ui.breeds

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.module.getViewModel
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.databinding.FragmentBreedsListBinding
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

class BreedsListFragment : BaseModuleFragment(), BaseBindClickHandler<CutePawsItemViewData> {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_breeds_list

    private lateinit var binding: FragmentBreedsListBinding
    private lateinit var breedsViewModel: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
        breedsViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
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
                getBreedListAllData()
            }

            // Set Swipe Refresh Layout
            binding.cutePawsListSwipeRefreshLayout?.initSwipe {
                getBreedListAllData()
            }
        }
    }

    private fun setBreedsData(viewData: CutePawsViewData?) {
        breedsViewModel.apply {

            // Notify observers
            isLoading.set(false)
            isError.set(false)
            binding.cutePawsListSwipeRefreshLayout?.isRefreshing = false

            // Set data
            cutePawsList.set(viewData?.breedList)

            // Keep position for recyclerview
            binding.cutePawsListRecyclerView.paginationForRecyclerScroll(itemPosition)

            getBreedsImageLiveData().value?.data?.let {
                setImageBreedsData(it)
            } ?: run {
                // Set Images for each breed
                viewData?.breedList?.forEach {
                    getRandomImageBreed(it.breed?.toLowerCase()!!)
                }
            }
        }
    }

    private fun setImageBreedsData(viewData: CutePawsViewData?) {
        breedsViewModel.isError.set(false)
        breedsViewModel.cutePawsList.get()?.forEach {
            if (viewData?.disclaimer?.contains(it.breed!!, ignoreCase = true) == true)
                it.imageUrl?.set(viewData.disclaimer)
        }
    }

    private fun getImageViewFromChild(view: View): ImageView {
        val cardView = (view as? CardView)
        val cl1 = (cardView?.getChildAt(0) as? ConstraintLayout)
        return (cl1?.getChildAt(0) as? ImageView)!!
    }

    override fun onClickView(view: View, item: CutePawsItemViewData) {
        val extras = FragmentNavigatorExtras(
            getImageViewFromChild(view) to "secondTransitionName"
        )
        ViewCompat.setTransitionName(getImageViewFromChild(view), "secondTransitionName")

        // Share Data
        sharedViewModel.breedItemViewData = item
        nextNavigate(
            nav = BreedsListFragmentDirections.actionCutePawsFragmentToBreedDetailFragment().actionId,
            extras = extras
        )
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
        implementObserver(breedsViewModel.getBreedsImageLiveData(),
            successBlock = { viewData -> setImageBreedsData(viewData) },
            errorBlock = { showErrorView() })
    }

    private fun showErrorView() {
        breedsViewModel.isError.set(true)
    }
}