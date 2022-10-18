package com.dsoft.presentation.ui.favourite.fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dsoft.domain.model.Favourite

class FavouriteDiffCallback(
    private val mOldFavouriteList: List<Favourite>,
    private val mNewFavouriteList: List<Favourite>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldFavouriteList.size

    override fun getNewListSize(): Int = mNewFavouriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavouriteList[oldItemPosition].cityName == mNewFavouriteList[newItemPosition].cityName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCity = mOldFavouriteList[oldItemPosition]
        val newCity = mNewFavouriteList[newItemPosition]

        return oldCity == newCity
    }

}