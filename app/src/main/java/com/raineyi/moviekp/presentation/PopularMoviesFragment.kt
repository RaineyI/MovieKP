package com.raineyi.moviekp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeIsLoading()
    }

    private fun observeIsLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarLoading.visibility = View.VISIBLE
            } else {
                binding.progressBarLoading.visibility = View.GONE
            }
        }
    }

    private fun setupLongClickListener() {
        moviesAdapter.onMovieLongClickListener = { movie ->
            if (movie.isFavourite) {
                viewModel.removeMovie(movie)
                viewModel.removeMovieDescription(movie.movieId)
            } else {
                viewModel.insertMovie(movie)
                viewModel.insertMovieDescription(movie)
            }
//            viewModel.getFavouriteMovie(movie.movieId).observe(this) {
//                if (it == null) {
//
//                } else {
//
//                }
//            }
        }
    }


    private fun launchSecondFragment(movie: MovieDto) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.movie_details_container, MovieDetailsFragment.newInstance(movie))
            .addToBackStack(null)
            .commit()
    }

    private fun setupClickListener() {
        moviesAdapter.onMovieClickListener = { movie ->

//TODO: Как узнать какой поворот экрана?
            if (activity.) {
                startActivity(
                    MovieDetailsActivity.newIntentMovieDetailsActivity(
                        requireContext(),
                        movie
                    )
                )
            } else {
                launchSecondFragment(movie)
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
        viewModel.listOfMovies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        setupClickListener()
        setupLongClickListener()
    }


    companion object {
        fun newInstance(): PopularMoviesFragment {
            return PopularMoviesFragment()
        }
    }

}