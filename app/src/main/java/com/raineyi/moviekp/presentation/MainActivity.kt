package com.raineyi.moviekp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.databinding.ActivityMainBinding
import com.raineyi.moviekp.presentation.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var moviesAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesAdapter = MoviesAdapter(object : MoviesAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadNextPage()
            }
        })
        binding.rvMovieList.adapter = moviesAdapter

        viewModel.listOfMovies.observe(this) {
            Log.d("TEST_API", it.toString())
            moviesAdapter.submitList(it)
        }

        viewModel.isLoading.observe(this) {
            if(it) {
                binding.progressBarLoading.visibility = View.VISIBLE
            } else {
                binding.progressBarLoading.visibility = View.GONE
            }
        }

        viewModel.getMovies()
    }
}