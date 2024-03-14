package com.raineyi.moviekp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.raineyi.moviekp.databinding.MovieItemBinding

class MovieViewHolder(
    val binding : MovieItemBinding
): RecyclerView.ViewHolder(binding.root) {
    val name = binding.tvName
    val poster = binding.imPoster
    val genresAndYear = binding.tvGenresAndYear
    val star = binding.imStar
}