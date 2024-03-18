package com.raineyi.moviekp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.ActivityMainBinding
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var movieDetailsContainer: FragmentContainerView? = null
    private var movieListContainer: FragmentContainerView? = null
//    private var _isOnePaneMode = MutableLiveData<Boolean>()
//    val isOnePaneMode: LiveData<Boolean>
//        get() = _isOnePaneMode

//    private val viewModel: PopularMoviesViewModel by lazy {
//        ViewModelProvider(this)[PopularMoviesViewModel::class.java]
//    }

//    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieDetailsContainer = findViewById(R.id.movie_details_container)
        launchFragmentPopular()
        setupClickListeners()
        checkFragmentDetails()
//        _isOnePaneMode.value = isOnePaneMode()
//        setupRecyclerView()
//        observeIsLoading()
    }

    private fun checkFragmentDetails() {

    }

    private fun launchFragmentPopular() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_list_container, PopularMoviesFragment.newInstance())
            .commit()
    }

    private fun launchFragmentFavourite() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_list_container, FavouriteMoviesFragment.newInstance())
            .commit()
    }

    private fun setupClickListeners() {
        binding.btPopular.setOnClickListener {
            launchFragmentPopular()
            binding.btFavorites.setBackgroundColor(ContextCompat.getColor(this, R.color.light_blue))
            binding.btFavorites.setTextColor(ContextCompat.getColor(this, R.color.blue))
            binding.btPopular.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            binding.btPopular.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        binding.btFavorites.setOnClickListener {
            launchFragmentFavourite()
            binding.btFavorites.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            binding.btFavorites.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btPopular.setBackgroundColor(ContextCompat.getColor(this, R.color.light_blue))
            binding.btPopular.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }
    }


//    private fun observeIsLoading() {
//        viewModel.isLoading.observe(this) {
//            if (it) {
//                binding.progressBarLoading.visibility = View.VISIBLE
//            } else {
//                binding.progressBarLoading.visibility = View.GONE
//            }
//        }
//    }

//    private fun setupLongClickListener() {
//        moviesAdapter.onMovieLongClickListener = { movie ->
//            if (movie.isFavourite) {
//                viewModel.removeMovie(movie)
//                viewModel.removeMovieDescription(movie.movieId)
//            } else {
//                viewModel.insertMovie(movie)
//                viewModel.insertMovieDescription(movie)
//            }
////            viewModel.getFavouriteMovie(movie.movieId).observe(this) {
////                if (it == null) {
////
////                } else {
////
////                }
////            }
//        }
//
//    }

//    private fun launchFragment(movie: MovieDto) {
//        supportFragmentManager.popBackStack()
//        supportFragmentManager.beginTransaction()
//            .add(R.id.movie_details_container, MovieDetailsFragment.newInstance(movie))
//            .addToBackStack(null)
//            .commit()
//    }

//    fun isOnePaneMode(): Boolean {
//        return movieDetailsContainer == null
//    }

//    private fun setupClickListener() {
//        moviesAdapter.onMovieClickListener = { movie ->
//            if (isOnePaneMode()) {
//                startActivity(MovieDetailsActivity.newIntentMovieDetailsActivity(this, movie))
//            } else {
//                launchFragment(movie)
//            }
//        }
//    }

//    private fun setupRecyclerView() {
//        moviesAdapter = MoviesAdapter(object : MoviesAdapter.OnLoadMoreListener {
//            override fun onLoadMore() {
//                viewModel.loadMovies()
//            }
//        })
//        binding.rvMovieList.adapter = moviesAdapter
////            recycledViewPool.setMaxRecycledViews(
////                MoviesAdapter.VIEW_TYPE_FAVOURITE,
////                MoviesAdapter.MAX_POOL_SIZE
////            )
////            recycledViewPool.setMaxRecycledViews(
////                MoviesAdapter.VIEW_TYPE_NETWORK,
////                MoviesAdapter.MAX_POOL_SIZE
////            )
//        viewModel.listOfMovies.observe(this) {
//            moviesAdapter.submitList(it)
//        }
//        setupClickListener()
//        setupLongClickListener()
//    }
}