package com.sangmeebee.searchmovieproject.ui.screen.searchmovie

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.sangmeebee.searchmovieproject.R
import com.sangmeebee.searchmovieproject.const.DEBOUNCE_DURATION_MILLIS
import com.sangmeebee.searchmovieproject.databinding.FragmentSearchMovieBinding
import com.sangmeebee.searchmovieproject.model.MovieModel
import com.sangmeebee.searchmovieproject.remote.util.EmptyQueryException
import com.sangmeebee.searchmovieproject.remote.util.HttpConnectionException
import com.sangmeebee.searchmovieproject.ui.adapter.FooterLoadStateAdapter
import com.sangmeebee.searchmovieproject.ui.adapter.SearchMovieAdapter
import com.sangmeebee.searchmovieproject.ui.base.BaseFragment
import com.sangmeebee.searchmovieproject.util.DividerDecoration
import com.sangmeebee.searchmovieproject.util.repeatOnStarted
import com.sangmeebee.searchmovieproject.util.textChangesToFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchMovieBinding>(FragmentSearchMovieBinding::inflate) {

    private val searchMovieViewModel by viewModels<SearchMovieViewModel>()
    private val movieAdapter: SearchMovieAdapter by lazy {
        SearchMovieAdapter(
            bookmark = searchMovieViewModel::fetchBookmark,
            navigateToDetailMovieFragment = ::navigateToDetailMovieFragment
        )
    }

    override fun FragmentSearchMovieBinding.setBinding() {
        lifecycleOwner = this@SearchMovieFragment
        viewModel = searchMovieViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObservable()
    }

    private fun setUpView() {
        setRecyclerView()
        setFavoriteButton()
        setSwipeRefreshLayout()
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
            adapter = movieAdapter.withLoadStateFooter(FooterLoadStateAdapter(movieAdapter::retry))
        }
    }

    private fun setFavoriteButton() {
        binding.llFavorite.setOnClickListener {
            navigateToBookmarkedMovieFragment()
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    private fun setUpObservable() {
        observeMovies()
        observeTextField()
        observeIsComplete()
        observePagingRefresh()
    }

    private fun observeMovies() = repeatOnStarted {
        searchMovieViewModel.movies.collectLatest { movies ->
            movieAdapter.submitData(movies)
        }
    }

    @OptIn(FlowPreview::class)
    fun observeTextField() = lifecycleScope.launch {
        binding.tilSearchMovie.editText?.let {
            it.textChangesToFlow()
                .debounce(DEBOUNCE_DURATION_MILLIS)
                .collectLatest { query ->
                    searchMovieViewModel.fetchQuery(query)
                }
        }
    }

    private fun observeIsComplete() = repeatOnStarted {
        searchMovieViewModel.uiState.map { it.isComplete }.distinctUntilChanged().collectLatest { isComplete ->
            if (isComplete) {
                binding.rvMovies.scrollToPosition(0)
                searchMovieViewModel.fetchIsComplete(false)
            }
        }
    }

    private fun observePagingRefresh() {
        repeatOnStarted {
            movieAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .collectLatest { loadStates ->
                    when (val refreshLoadState = loadStates.refresh) {
                        is LoadState.Loading -> {
                            searchMovieViewModel.fetchIsLoading(true)
                        }
                        is LoadState.Error -> {
                            searchMovieViewModel.fetchIsLoading(false)
                            checkErrorState(refreshLoadState.error)
                        }
                        is LoadState.NotLoading -> {
                            if (searchMovieViewModel.uiState.value.isLoading) {
                                searchMovieViewModel.fetchIsComplete(true)
                            }
                            searchMovieViewModel.fetchErrorMessage(null)
                            searchMovieViewModel.fetchIsLoading(false)
                        }
                    }
                }
        }
    }

    private fun checkErrorState(throwable: Throwable) {
        when (throwable) {
            is EmptyQueryException -> searchMovieViewModel.fetchErrorMessage(resources.getString(R.string.search_movie_empty_query))
            is HttpConnectionException -> searchMovieViewModel.fetchErrorMessage(resources.getString(R.string.all_network_disconnect))
            else -> searchMovieViewModel.fetchErrorMessage(resources.getString(R.string.all_unknown_error))
        }
    }

    private fun navigateToDetailMovieFragment(movie: MovieModel) {
        // TODO 영화 상세 화면으로 이동
    }

    private fun navigateToBookmarkedMovieFragment() {
        val action = SearchMovieFragmentDirections.actionSearchMovieFragmentToBookmarkedMovieFragment()
        findNavController().navigate(action)
    }
}
