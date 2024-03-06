package com.raineyi.moviekp.presentation.adapters


import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.MovieItemBinding

class MoviesAdapter(private val onLoadMoreListener: OnLoadMoreListener) :
    ListAdapter<MovieDto, MovieViewHolder>(com.raineyi.moviekp.presentation.adapters.MovieItemDiffCallback()) {

    //TODO: onLoadMoreListener
    var onMovieLongClickListener: ((MovieDto) -> Unit)? = null
    var onMovieClickListener: ((MovieDto) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return com.raineyi.moviekp.presentation.adapters.MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        val binding = holder.binding
        Glide.with(holder.poster)
            .load(movieItem.posterUrl)
            .into(holder.poster)
        holder.name.text = movieItem.name
        val movieGenres = movieItem.genres?.get(0)?.genre?.replaceFirstChar { it.uppercase() }
        holder.genre.text = movieGenres.toString()
//        val year = String.format(getString(R.string.year), movieItem.year.toString())
//        holder.year.text = String.format(
//            getString(R.string.year),
//            movieItem.year.toString()
//        )
        holder.year.text = "(${movieItem.year.toString()})"

        if (position >= currentList.size - 10) {
            onLoadMoreListener.onLoadMore()
        }

        binding.root.setOnLongClickListener {
            onMovieLongClickListener?.invoke(movieItem)
            true
        }

        binding.root.setOnClickListener {
            onMovieClickListener?.invoke(movieItem)
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}

