package com.raineyi.moviekp.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raineyi.moviekp.R
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movie: Movie
    private lateinit var description: Description
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        parseParams()
        if (savedInstanceState == null) {
            launchFragment()
        }
    }

    private fun parseParams() {
        when {
            Build.VERSION.SDK_INT >= 33 -> intent.getParcelableExtra(
                EXTRA_MOVIE,
                Movie::class.java
            )

            else -> intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        }?.let {
            movie = it
        }

        when {
            Build.VERSION.SDK_INT >= 33 -> intent.getParcelableExtra(
                EXTRA_DESCRIPTION,
                Description::class.java
            )

            else -> intent.getParcelableExtra<Description>(EXTRA_MOVIE)
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
            movie: Movie,
            description: Description
        ): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_DESCRIPTION, description)
            return intent
        }
    }
}