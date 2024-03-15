package com.raineyi.moviekp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.databinding.FragmentMovieDetailsBinding
import com.raineyi.moviekp.databinding.FragmentPopularMoviesBinding
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter
import java.lang.RuntimeException

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding: FragmentPopularMoviesBinding
        get() = _binding ?: throw RuntimeException("FragmentPopularMoviesBinding == null")

    private val viewModel: PopularMoviesViewModel by lazy {
        ViewModelProvider(this)[PopularMoviesViewModel::class.java]
    }

    private lateinit var moviesAdapter: MoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupClickListener() {
        moviesAdapter.onMovieClickListener = { movie ->
            if (isOnePaneMode()) {
                startActivity(MovieDetailsActivity.newIntentMovieDetailsActivity(this, movie))
            } else {
                launchFragment(movie)
            }
        }
    }
    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter(object : MoviesAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadMovies()
            }
        })
        binding.rvMovieList.adapter = moviesAdapter
//            recycledViewPool.setMaxRecycledViews(
//                MoviesAdapter.VIEW_TYPE_FAVOURITE,
//                MoviesAdapter.MAX_POOL_SIZE
//            )
//            recycledViewPool.setMaxRecycledViews(
//                MoviesAdapter.VIEW_TYPE_NETWORK,
//                MoviesAdapter.MAX_POOL_SIZE
//            )
        viewModel.listOfMovies.observe(this) {
            moviesAdapter.submitList(it)
        }
        setupClickListener()
        setupLongClickListener()
    }
}