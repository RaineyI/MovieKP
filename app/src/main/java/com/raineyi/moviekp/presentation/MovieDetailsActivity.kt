package com.raineyi.moviekp.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieDto

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movie: MovieDto
    private lateinit var description: DescriptionDto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        parseParams()
        Log.d("FRAGMENT_TEST", "MovieDetailsActivity")
        if (savedInstanceState == null) {
            launchFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FRAGMENT_TEST","Activity: onDestroy")
    }

    private fun parseParams() {
        when {
            Build.VERSION.SDK_INT >= 33 -> intent.getParcelableExtra(
                EXTRA_MOVIE,
                MovieDto::class.java
            )

            else -> intent.getParcelableExtra<MovieDto>(EXTRA_MOVIE)
        }?.let {
            movie = it
        }

        when {
            Build.VERSION.SDK_INT >= 33 -> intent.getParcelableExtra(
                EXTRA_DESCRIPTION,
                DescriptionDto::class.java
            )

            else -> intent.getParcelableExtra<DescriptionDto>(EXTRA_MOVIE)
        }?.let {
            description = it
        }
    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.movie_details_container,
                MovieDetailsFragment.newInstance(movie, description)
            )
            .commit()
    }

    companion object {
        private const val EXTRA_MOVIE = "extra movie"
        private const val EXTRA_DESCRIPTION = "extra_description"
        fun newIntentMovieDetailsActivity(
            context: Context,
            movie: MovieDto,
            description: DescriptionDto
        ): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_DESCRIPTION, description)
            return intent
        }
    }
}