package com.raineyi.moviekp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.FragmentPopularMoviesBinding
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter

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
//                viewModel.removeMovieDescription(movie.movieId)
            } else {
                viewModel.insertMovie(movie)
//                viewModel.insertMovieDescription(movie)
            }
            //TODO: Если нет описания, вылетает с ошибкой.
        }
    }

    private fun isOnePaneMode(): Boolean {
        val containerDetails = requireActivity().findViewById<View>(R.id.movie_details_container)
        return containerDetails == null
    }


    private fun launchDetailsFragment(container: Int, movie: MovieDto, description: DescriptionDto) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                container,
                MovieDetailsFragment.newInstance(movie, description)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun setupClickListener() {
        moviesAdapter.onMovieClickListener = { movie ->
            viewModel.loadDescription(movie)
            viewModel.description.observe(viewLifecycleOwner) { description ->
                val container =
                //                if (isOnePaneMode())
//                    R.id.movie_list_container
//                else
                    R.id.movie_details_container

                if (isOnePaneMode()) {
                    startActivity(
                        MovieDetailsActivity.newIntentMovieDetailsActivity(
                            requireContext(),
                            movie,
                            description
                        )
                    )
                } else {
                    launchDetailsFragment(container, movie, description)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter.onLoadMoreListener = {
            viewModel.loadMovies()
        }
        binding.rvMovieList.adapter = moviesAdapter
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