package com.raineyi.moviekp.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.MovieItemBinding

class MoviesAdapter(private val onLoadMoreListener: OnLoadMoreListener) :
    ListAdapter<MovieDto, MovieViewHolder>(MovieItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        Glide.with(holder.poster)
            .load(movieItem.posterUrl)
            .into(holder.poster)
        holder.name.text = movieItem.name
        val movieGenres = movieItem.genres?.get(0)?.genre?.replaceFirstChar { it.uppercase() }
        holder.genre.text = movieGenres.toString()
        holder.year.text = "(${movieItem.year.toString()})"

        if (position >= currentList.size - 1) {
            onLoadMoreListener.onLoadMore()
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}

