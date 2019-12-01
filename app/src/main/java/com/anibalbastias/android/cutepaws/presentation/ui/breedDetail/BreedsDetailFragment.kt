package com.anibalbastias.android.cutepaws.presentation.ui.breedDetail

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
import androidx.transition.TransitionInflater
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.module.getViewModel
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.base.view.ResourceState
import com.anibalbastias.android.cutepaws.databinding.FragmentBreedsDetailBinding
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

class BreedsDetailFragment : BaseModuleFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_breeds_detail

    private lateinit var binding: FragmentBreedsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavController(this@BreedsDetailFragment.view)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentBreedsDetailBinding
        //binding.cutePawsViewModel = breedsViewModel
        //binding.cutePawsItemListener = this
        binding.sharedViewModel = sharedViewModel
        binding.lifecycleOwner = this

        initToolbar()
        //initViewModel()
        fetchImagesByBreed()
    }

    private fun fetchImagesByBreed() {

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
}