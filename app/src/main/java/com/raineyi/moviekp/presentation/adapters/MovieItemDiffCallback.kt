package com.raineyi.moviekp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.domain.entities.Movie

class MovieItemDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}