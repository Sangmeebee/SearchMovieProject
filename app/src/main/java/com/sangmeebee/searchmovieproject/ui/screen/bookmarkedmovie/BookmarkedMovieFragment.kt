package com.sangmeebee.searchmovieproject.ui.screen.bookmarkedmovie

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovieproject.R
import com.sangmeebee.searchmovieproject.databinding.FragmentBookmarkedMovieBinding
import com.sangmeebee.searchmovieproject.model.MovieModel
import com.sangmeebee.searchmovieproject.ui.adapter.BookmarkMovieAdapter
import com.sangmeebee.searchmovieproject.ui.base.BaseFragment
import com.sangmeebee.searchmovieproject.util.DividerDecoration
import com.sangmeebee.searchmovieproject.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BookmarkedMovieFragment : BaseFragment<FragmentBookmarkedMovieBinding>(FragmentBookmarkedMovieBinding::inflate) {

    private val bookmarkedMovieViewModel by viewModels<BookmarkedMovieViewModel>()
    private val bookmarkedMovieAdapter: BookmarkMovieAdapter by lazy {
        BookmarkMovieAdapter(
            bookmark = bookmarkedMovieViewModel::fetchBookmark,
            navigateToDetailMovieFragment = ::navigateToDetailMovieFragment
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setRecyclerView()
        observeBookmarkedMovies()
    }

    private fun setToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setRecyclerView() {
        binding.rvMovies.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerDecoration(
                    headerPadding = 8,
                    separatorPadding = 8,
                    footerPadding = 20,
                    divideHeight = 1,
                    divideColor = ContextCompat.getColor(requireContext(), R.color.gray_300)
                )
            )
            adapter = bookmarkedMovieAdapter
        }
    }

    private fun observeBookmarkedMovies() = repeatOnStarted {
        bookmarkedMovieViewModel.bookmarkedMovies.collectLatest {
            bookmarkedMovieAdapter.submitList(it)
        }
    }

    private fun navigateToDetailMovieFragment(movie: MovieModel) {
        // TODO 영화 상세 화면으로 이동
    }
}
