package com.sangmeebee.searchmovieproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovieproject.domain.util.HttpConnectionException
import com.sangmeebee.searchmovieproject.presentation.R
import com.sangmeebee.searchmovieproject.presentation.databinding.ItemLoadStateFooterBinding

class FooterLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<FooterLoadStateAdapter.LoadStateFooterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadStateFooterViewHolder {
        val binding = ItemLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.llReload.setOnClickListener { retry() }
        return LoadStateFooterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateFooterViewHolder(private val binding: ItemLoadStateFooterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.pbLoad.isVisible = loadState is LoadState.Loading
            binding.llReload.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                binding.errorMessage = when (loadState.error) {
                    is HttpConnectionException -> binding.root.context.resources.getString(R.string.all_network_disconnect)
                    else -> binding.root.context.resources.getString(R.string.all_unknown_error)
                }
            }
        }
    }
}
