package com.sangmeebee.searchmovieproject.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovieproject.presentation.databinding.ItemMovieBinding
import com.sangmeebee.searchmovieproject.presentation.model.MovieModel

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieModel?) {
        if (movie != null) {
            binding.movie = movie
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(
        oldItem: MovieModel,
        newItem: MovieModel,
    ): Boolean = oldItem.link == newItem.link

    override fun areContentsTheSame(
        oldItem: MovieModel,
        newItem: MovieModel,
    ): Boolean = oldItem == newItem
}
