package com.raineyi.moviekp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.R
import com.raineyi.moviekp.databinding.ActivityMainBinding
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var movieDetailsContainer: FragmentContainerView? = null

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var moviesAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieDetailsContainer = findViewById(R.id.movie_details_container)

        moviesAdapter = MoviesAdapter(object : MoviesAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadMovies()
            }
        })
        binding.rvMovieList.adapter = moviesAdapter

        viewModel.listOfMovies.observe(this) {
            moviesAdapter.submitList(it)
        }

        viewModel.isLoading.observe(this) {
            if(it) {
                binding.progressBarLoading.visibility = View.VISIBLE
            } else {
                binding.progressBarLoading.visibility = View.GONE
            }
        }

        moviesAdapter.onMovieClickListener = {
            if(isOnePaneMode()) {
                startActivity(MovieDetailsActivity.newIntentMovieDetailsActivity(this))
            } else {
                launchFragment()
            }
        }
    }

    private fun launchFragment(){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.movie_details_container, MovieDetailsFragment.newInstance("value"))
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return movieDetailsContainer == null
    }
}