package com.sangmeebee.searchmovieproject.ui.screen.detailmovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sangmeebee.searchmovieproject.databinding.FragmentDetailMovieBinding
import com.sangmeebee.searchmovieproject.ui.base.BaseFragment
import com.sangmeebee.searchmovieproject.ui.base.CustomWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>(FragmentDetailMovieBinding::inflate) {

    private val detailMovieViewModel by viewModels<DetailMovieViewModel>()
    private val args: DetailMovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailMovieViewModel.fetchMovie(args.movie)
    }

    override fun FragmentDetailMovieBinding.setBinding() {
        lifecycleOwner = this@DetailMovieFragment
        viewModel = detailMovieViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebView()
        setToolbar()
        setOnBackPressedDispatcher()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        binding.webview.apply {
            settings.javaScriptEnabled = true
            webViewClient = CustomWebViewClient()
            isVerticalScrollBarEnabled = false
            settings.apply {
                builtInZoomControls = true
                setSupportZoom(true)
                displayZoomControls = false
            }
            loadUrl(args.movie.link)
        }
    }

    private fun setToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.webview.canGoBack()) {
                binding.webview.goBack()
            } else {
                findNavController().navigateUp()
            }
        }
    }
}
