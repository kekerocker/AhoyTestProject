package com.dsoft.presentation.ui.favourite.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.domain.model.Favourite
import com.dsoft.presentation.databinding.ItemFavouriteCityBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {
    private val listFavourites: MutableList<Favourite> = mutableListOf()

    var deleteClickListener: ((Favourite) -> Unit)? = null
    var clickListener: ((Favourite) -> Unit)? = null

    fun updateList(newData: List<Favourite>) {
        val diffCallback = FavouriteDiffCallback(listFavourites, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listFavourites.clear()
        this.listFavourites.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavouriteViewHolder(binding: ItemFavouriteCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTitle: MaterialTextView = binding.tvTextItem
        val btnDelete: MaterialButton = binding.btnDelete
        val btnChooseFavourite: ConstraintLayout = binding.btnChooseFavourite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            ItemFavouriteCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val currentItem = listFavourites[position]

        holder.tvTitle.text = currentItem.cityName
        holder.btnDelete.setOnClickListener { deleteClickListener?.invoke(currentItem) }
        holder.btnChooseFavourite.setOnClickListener { clickListener?.invoke(currentItem) }
    }

    override fun getItemCount(): Int = listFavourites.size
}