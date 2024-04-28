package com.raineyi.moviekp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.raineyi.moviekp.domain.entities.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}


//class MovieDiffCallback(
//    private val oldList: List<Movie>,
//    private val newList: List<Movie>
//) : DiffUtil.Callback() {
//
//    override fun getOldListSize(): Int = oldList.size
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition].movieId == newList[newItemPosition].movieId
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
//    }
//}