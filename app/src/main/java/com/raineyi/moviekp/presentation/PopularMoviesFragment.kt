package com.raineyi.moviekp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.databinding.FragmentPopularMoviesBinding
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter
import com.raineyi.moviekp.presentation.viewmodels.PopularMoviesViewModel
import com.raineyi.moviekp.presentation.viewmodels.ViewModelFactory
import com.raineyi.moviekp.presentation.viewmodels.observeOnce
import javax.inject.Inject

class PopularMoviesFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding: FragmentPopularMoviesBinding
        get() = _binding ?: throw RuntimeException("FragmentPopularMoviesBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PopularMoviesViewModel::class.java]
    }

    private lateinit var moviesAdapter: MoviesAdapter

    private val component by lazy {
        (requireActivity().application as MovieApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

            viewModel.getFavouriteMovie(movie.movieId)
                .observeOnce(viewLifecycleOwner) { movieFromDb ->
                    if (movieFromDb == null) {
                        viewModel.loadDescription(movie)
                            .observe(viewLifecycleOwner) { description ->
                                description?.let {
                                    viewModel.insertMovie(movie, description)
                                }
                            }
                    } else {
                        viewModel.removeMovie(movie)
                    }
                }
        }
    }

    private fun isOnePaneMode(): Boolean {
        val containerDetails = requireActivity().findViewById<View>(R.id.movie_details_container)
        return containerDetails == null
    }

    private fun launchDetailsFragment(movie: Movie, description: Description) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.movie_details_container,
                MovieDetailsFragment.newInstance(movie, description)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun setupClickListener() {
        moviesAdapter.onMovieClickListener = { movie ->

            viewModel.loadDescription(movie).observe(viewLifecycleOwner) { description ->
                description?.let {
                    if (isOnePaneMode()) {
                        startActivity(
                            MovieDetailsActivity.newIntentMovieDetailsActivity(
                                requireContext(),
                                movie,
                                description
                            )
                        )
                    } else {
                        launchDetailsFragment(movie, description)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter.onLoadMoreListener = {
            viewModel.loadMovies()
        }
        with(binding.rvMovieList) {
            binding.rvMovieList.adapter = moviesAdapter
            binding.rvMovieList.recycledViewPool.setMaxRecycledViews(
                MoviesAdapter.VIEW_TYPE_FAVOURITE,
                MoviesAdapter.MAX_POOL_SIZE
            )
            binding.rvMovieList.recycledViewPool.setMaxRecycledViews(
                MoviesAdapter.VIEW_TYPE_NETWORK,
                MoviesAdapter.MAX_POOL_SIZE
            )
        }
        viewModel.listOfPopularMovies.observe(viewLifecycleOwner) { popularMovies ->
            if (popularMovies != null) {

                viewModel.getFavouriteMovieList.observe(viewLifecycleOwner) { favouriteMovies ->
                    if (favouriteMovies != null) {

                        val updatedMovies = popularMovies.map { popularMovie ->
                            val isFavourite =
                                favouriteMovies.any { it.movieId == popularMovie.movieId }
                            popularMovie.copy(isFavourite = isFavourite)

                        }
                        moviesAdapter.submitList(updatedMovies)
                    }
                }
            }
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