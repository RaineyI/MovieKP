package com.raineyi.moviekp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.raineyi.moviekp.databinding.MovieItemBinding

class MovieViewHolder(binding : MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val poster = binding.imPoster
    val name = binding.tvName
    val genre = binding.tvGenre
    val year = binding.tvYear
}