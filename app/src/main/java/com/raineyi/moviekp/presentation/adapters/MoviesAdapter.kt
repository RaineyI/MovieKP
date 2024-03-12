package com.raineyi.moviekp.presentation.adapters


import android.content.res.Resources
import android.graphics.BitmapFactory
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.MovieItemBinding
import kotlinx.coroutines.withContext

class MoviesAdapter(private val onLoadMoreListener: OnLoadMoreListener) :
    ListAdapter<MovieDto, MovieViewHolder>(MovieItemDiffCallback()) {

    //TODO: onLoadMoreListener
    var onMovieLongClickListener: ((MovieDto) -> Unit)? = null
    var onMovieClickListener: ((MovieDto) -> Unit)? = null
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
        val binding = holder.binding
        Glide.with(holder.poster)
            .load(movieItem.posterUrl)
            .into(holder.poster)
        holder.name.text = movieItem.name
        val genres = movieItem.genres.joinToString(", ") {it.genre}
        holder.genresAndYear.text = String.format(
            holder.itemView.context.getString(R.string.genres_and_year),
            genres.replaceFirstChar { it.uppercase() },
            movieItem.year.toString()
        )

        if(movieItem.isFavourite) {
            holder.star.setImageResource(R.drawable.im_star)
        } else {
            holder.star.setImageResource(R.drawable.im_grey_star)
        }

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

