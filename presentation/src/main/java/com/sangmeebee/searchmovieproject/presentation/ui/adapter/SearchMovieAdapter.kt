package com.sangmeebee.searchmovieproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.sangmeebee.searchmovieproject.presentation.databinding.ItemMovieBinding
import com.sangmeebee.searchmovieproject.presentation.model.MovieModel

class SearchMovieAdapter(
    private val bookmark: (MovieModel) -> Unit,
    private val navigateToDetailMovieFragment: (MovieModel) -> Unit,
) : PagingDataAdapter<MovieModel, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MovieViewHolder(binding)
        binding.ivBookmark.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { movie -> bookmark(movie) }
        }
        binding.clContainer.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { movie -> navigateToDetailMovieFragment(movie) }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))
}
