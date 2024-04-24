package com.raineyi.moviekp.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.FragmentPopularMoviesBinding
import com.raineyi.moviekp.di.ApplicationComponent
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter
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
        val movieId = 817167
//        viewModel.getFavouriteMovie(movieId).observe(viewLifecycleOwner) { movie ->
//            Log.d("TEST_DB", "${movie.name}")
//        }

        //Проблемы с null ??

//        moviesAdapter.onMovieLongClickListener = { movie ->
//            Log.d("TEST_DB", "Click")
//            Log.d("TEST_DB", "${movie.name}")
//            try {
//                viewModel.getFavouriteMovie(movie.movieId).observe(viewLifecycleOwner) { movieFromDb ->
//                    try {
//                        if (movieFromDb == null) {
//                            Log.d("TEST_DB", "null")
//                        } else {
//                            Log.d("TEST_DB", "not null")
//                        }
//                    } catch (e: Exception) {
//                        Log.e("TEST_DB", "Error while processing movie data: ${e.message}")
//                        // Дополнительная обработка ошибки, если не удалось обработать данные о фильме
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("TEST_DB", "Error while getting favorite movie data: ${e.message}")
//                // Дополнительная обработка ошибки, если возникла ошибка при получении данных о фильме из базы данных
//            }
    }


//    private fun setupLongClickListener() {
//        Log.d("TEST_DB", "Click")
//        moviesAdapter.onMovieLongClickListener = { movie ->
//
//            viewModel.getFavouriteMovie(movie.movieId).observe(viewLifecycleOwner) { movieFromDb ->
//                if (movieFromDb == null) {
//                    Log.d("TEST_DB", "null")
//                } else {
//                    Log.d("TEST_DB", "not null")
//                }
//
//            }
//        }
//    }

//        moviesAdapter.onMovieLongClickListener = { movie ->
//            Log.d("TEST_DB", "LongClick")
//            Log.d("TEST_DB", movie.toString())
//            viewModel.loadDescription(movie).observe(viewLifecycleOwner) { description ->
//                Log.d("TEST_DB", description.toString())
//                description?.let {
//                    viewModel.getFavouriteMovie(movie.movieId)
//                        .observe(viewLifecycleOwner) { movieFromDb ->
//                            Log.d("TEST_DB", "observe FavouriteMovie")
//                            if (movieFromDb == null) {
//                                Log.d("TEST_DB", "getFavouriteMovie return null")
//                                viewModel.insertMovie(movie, description)
//                            } else {
//                                Log.d("TEST_DB", "getFavouriteMovie return movie")
//                                viewModel.removeMovie(movie, description)
//                            }
//
//                        }
//                }
//            }
//        }

//
//                if(movie.isFavourite) {
//                    viewModel.removeMovie(movie)
//                } else {
//                    viewModel.insertMovie(movie)
//                }
//            viewModel.loadDescription(movie).observe(viewLifecycleOwner) { description ->
//                Log.d("TEST_DB", description.toString())
//                description?.let {
//                    if (movie.isFavourite) {
//
//                    } else {
//
//                    }
//                }
//            }


//            movie.movieId.let {
//                viewModel.description.observe(viewLifecycleOwner) { description ->
//                    description?.let {
//                        if (movie.isFavourite) {
//                            viewModel.removeMovie(movie)
//                        } else {
//                            viewModel.insertMovie(movie)
//                        }
//                    }
//                }
//            }
    //TODO: Если нет описания, вылетает с ошибкой.
//        }
//    }

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
//            Log.d("TEST_API", "Click")
//            Log.d("TEST_API", "${movie.name}")

            viewModel.loadDescription(movie).observe(viewLifecycleOwner) { description ->
//                Log.d("TEST_API", "observe: ${description.toString()}")
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