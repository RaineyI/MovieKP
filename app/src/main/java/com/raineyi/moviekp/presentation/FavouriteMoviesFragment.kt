package com.raineyi.moviekp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.database.dbmodel.DescriptionDbModel
import com.raineyi.moviekp.data.mapper.DescriptionMapper
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.FragmentFavouriteMoviesBinding
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter

class FavouriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavouriteMoviesBinding? = null
    private val binding: FragmentFavouriteMoviesBinding
        get() = _binding ?: throw RuntimeException("FragmentPopularMoviesBinding == null")

    private val viewModel: FavouriteMoviesViewModel by lazy {
        ViewModelProvider(this)[FavouriteMoviesViewModel::class.java]
    }

    private lateinit var moviesAdapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupLongClickListener() {
        moviesAdapter.onMovieLongClickListener = { movie ->
            movie.movieId?.let {
                viewModel.getDbDescription(movie.movieId).observe(viewLifecycleOwner) {description ->
                    viewModel.removeMovie(movie, description)
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
            movie.movieId?.let {
                viewModel.getDbDescription(movie.movieId)
                    .observe(viewLifecycleOwner) { description ->
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
        binding.rvFavouriteMovieList.adapter = moviesAdapter
        viewModel.listOfMovies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        setupClickListener()
        setupLongClickListener()
    }

    companion object {
        fun newInstance(): FavouriteMoviesFragment {
            return FavouriteMoviesFragment()
        }
    }
}