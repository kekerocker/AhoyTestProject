package com.dsoft.presentation.ui.favourite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.dsoft.domain.model.Favourite
import com.dsoft.presentation.databinding.BottomSheetFragmentFavouriteBinding
import com.dsoft.presentation.ui.favourite.fragment.adapter.FavouriteAdapter
import com.dsoft.presentation.ui.favourite.vm.FavouriteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FavouriteViewModel>()

    private lateinit var rvAdapter: FavouriteAdapter

    companion object {
        const val FAVOURITE_RESULT_KEY = "favourite_result_key"
        const val FAVOURITE_KEY = "favourite_key"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = BottomSheetFragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObserver()
    }

    private fun initRecycler() {
        rvAdapter = FavouriteAdapter().apply {
            deleteClickListener = (viewModel::deleteCityFromFavourites)
            clickListener = (::initFragmentResultListener)
        }
        binding.recyclerViewFavouriteList.adapter = rvAdapter
    }

    private fun initObserver() {
        viewModel.favouriteCities.observe(viewLifecycleOwner) {
            when (it.isEmpty()) {
                true -> binding.tvNoFavourites.visibility = VISIBLE
                false -> binding.tvNoFavourites.visibility = GONE
            }
            it?.let(rvAdapter::updateList)
        }
    }

    private fun initFragmentResultListener(favourite: Favourite) {
        requireActivity().supportFragmentManager.setFragmentResult(
            FAVOURITE_RESULT_KEY,
            bundleOf(FAVOURITE_KEY to favourite)
        )
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}