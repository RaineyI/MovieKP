package com.raineyi.moviekp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.raineyi.moviekp.data.network.model.MovieDto

class MovieItemDiffCallback: DiffUtil.ItemCallback<MovieDto>() {
    override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem == newItem
    }
}