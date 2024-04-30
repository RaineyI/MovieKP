package com.raineyi.moviekp.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.raineyi.moviekp.R
import com.raineyi.moviekp.databinding.MovieFavouriteItemBinding
import com.raineyi.moviekp.databinding.MovieItemBinding
import com.raineyi.moviekp.domain.entities.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback) {

    var onLoadMoreListener: (() -> Unit)? = null
    var onMovieLongClickListener: ((Movie) -> Unit)? = null
    var onMovieClickListener: ((Movie) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_FAVOURITE ->
                MovieFavouriteItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            VIEW_TYPE_NETWORK ->
                MovieItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        val binding = holder.binding

        when (binding) {
            is MovieFavouriteItemBinding -> {
                with(binding) {
                    Picasso.get()
                        .load(movieItem.posterUrl)
                        .into(imPoster)
                    tvName.text = movieItem.name
                    tvGenresAndYear.text = String.format(
                        root.context.getString(R.string.genres_and_year),
                        movieItem.genres,
                        movieItem.year.toString()
                    )
                }
            }

            is MovieItemBinding -> {
                with(binding) {
                    Picasso.get()
                        .load(movieItem.posterUrl)
                        .into(imPoster)
                    tvName.text = movieItem.name
                    tvGenresAndYear.text = String.format(
                        root.context.getString(R.string.genres_and_year),
                        movieItem.genres,
                        movieItem.year.toString()
                    )
                }
            }
        }

        if (position >= currentList.size - 10) {
            onLoadMoreListener?.invoke()
        }

        binding.root.setOnLongClickListener {
            onMovieLongClickListener?.invoke(movieItem)
            true
        }

        binding.root.setOnClickListener {
            onMovieClickListener?.invoke(movieItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val movie = getItem(position)
        return if (movie.isFavourite) {
            VIEW_TYPE_FAVOURITE
        } else {
            VIEW_TYPE_NETWORK
        }
    }

    companion object {
        const val VIEW_TYPE_NETWORK = 0
        const val VIEW_TYPE_FAVOURITE = 1

        const val MAX_POOL_SIZE = 30
    }
}
