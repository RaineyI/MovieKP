package com.raineyi.moviekp.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(isNetworkAvailable(this)) {
            launchFragmentPopular()
        } else {
            showError()
        }
        setupClickListeners()
    }


    private fun hideError() {
        binding.imError.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.btRetry.visibility = View.GONE
        binding.btPopular.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.imError.visibility = View.VISIBLE
        binding.tvError.visibility = View.VISIBLE
        binding.btRetry.visibility = View.VISIBLE
        binding.btRetry.setOnClickListener {
            if(isNetworkAvailable(this)) {
                launchFragmentPopular()
            } else {
                showError()
            }
        }
        binding.btPopular.visibility = View.GONE
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun launchFragmentPopular() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_list_container, PopularMoviesFragment.newInstance())
            .commit()
        hideError()
    }

    private fun launchFragmentFavourite() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_list_container, FavouriteMoviesFragment.newInstance())
            .commit()
        hideError()
    }

    private fun setupClickListeners() {
        binding.btPopular.setOnClickListener {
            if(isNetworkAvailable(this)) {
                launchFragmentPopular()
            } else {
                binding.movieListContainer.removeAllViews()
                showError()
            }
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
}