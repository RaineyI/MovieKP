package com.raineyi.moviekp.presentation.adapters


import android.content.res.Resources
import android.graphics.BitmapFactory
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.MovieFavouriteItemBinding
import com.raineyi.moviekp.databinding.MovieItemBinding
import kotlinx.coroutines.withContext

//private val onLoadMoreListener: OnLoadMoreListener
class MoviesAdapter() :
    ListAdapter<MovieDto, MovieViewHolder>(MovieItemDiffCallback()) {

    //TODO: onLoadMoreListener
    var onLoadMoreListener: (() -> Unit)? = null
    var onMovieLongClickListener: ((MovieDto) -> Unit)? = null
    var onMovieClickListener: ((MovieDto) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
//        val binding = when (viewType) {
//            VIEW_TYPE_FAVOURITE ->
//                MovieFavouriteItemBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//
//            VIEW_TYPE_NETWORK ->
//                MovieItemBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        val binding = holder.binding
        binding.tvName

        Glide.with(holder.poster)
            .load(movieItem.posterUrl)
            .into(binding.imPoster)
        holder.name.text = movieItem.name
        val genres = movieItem.genres.joinToString(", ") { it.genre }
        holder.genresAndYear.text = String.format(
            holder.itemView.context.getString(R.string.genres_and_year),
            genres.replaceFirstChar { it.uppercase() },
            movieItem.year.toString()
        )
        if (movieItem.isFavourite) {
            holder.star.visibility = View.VISIBLE
        } else {
            holder.star.visibility = View.GONE
        }

//        when (binding) {
//            is MovieFavouriteItemBinding -> {
//                Glide.with(binding.imPoster)
//                    .load(movieItem.posterUrl)
//                    .into(binding.imPoster)
//                binding.tvName.text = movieItem.name
//                val genres = movieItem.genres.joinToString(", ") { it.genre }
//                binding.tvGenresAndYear.text = String.format(
//                    holder.itemView.context.getString(R.string.genres_and_year),
//                    genres.replaceFirstChar { it.uppercase() },
//                    movieItem.year.toString()
//                )
//            }

//            is MovieItemBinding -> {
//                Glide.with(binding.imPoster)
//                    .load(movieItem.posterUrl)
//                    .into(binding.imPoster)
//                binding.tvName.text = movieItem.name
//                val genres = movieItem.genres.joinToString(", ") { it.genre }
//                binding.tvGenresAndYear.text = String.format(
//                    holder.itemView.context.getString(R.string.genres_and_year),
//                    genres.replaceFirstChar { it.uppercase() },
//                    movieItem.year.toString()
//                )
//            }


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

//    override fun getItemViewType(position: Int): Int {
//        val movie = getItem(position)
//        return if (movie.isFavourite) {
//            VIEW_TYPE_FAVOURITE
//        } else {
//            VIEW_TYPE_NETWORK
//        }
//    }

//    interface OnLoadMoreListener {
//        fun onLoadMore()
//    }

//    companion object {
//        const val VIEW_TYPE_NETWORK = 0
//        const val VIEW_TYPE_FAVOURITE = 1
//
//        const val MAX_POOL_SIZE = 30
//    }
}

