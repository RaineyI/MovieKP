package com.raineyi.moviekp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.MovieItemBinding

class MoviesAdapter : ListAdapter<MovieDto, MovieViewHolder>(MovieItemDiffCallback()) {

//    private lateinit var OnReachEndListener: OnReachEndListener

    var onReachEndListener: (() -> Unit)? = null

    //    private var onMovieClickListener : ((Movie) -> Unit)? = null
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
        holder.year.text = movieItem.year.toString()

        if (position == currentList.size - 1) {
            onReachEndListener?.invoke()
        }
//
//        holder.itemView.setOnClickListener {
//            onMovieClickListener?.invoke(movieItem)
//        }
    }


//    internal interface OnReachEndListener {
//        fun onReachEnd()
//    }

}