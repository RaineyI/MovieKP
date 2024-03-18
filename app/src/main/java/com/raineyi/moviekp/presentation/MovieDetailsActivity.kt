package com.raineyi.moviekp.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movie: MovieDto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        parseParam()
        if(savedInstanceState == null) {
            launchFragment()
        }
    }

    private fun parseParam() {
        when {
            Build.VERSION.SDK_INT >= 33 -> intent.getParcelableExtra(
                EXTRA_MOVIE,
                MovieDto::class.java
            )
            else -> intent.getParcelableExtra<MovieDto>(EXTRA_MOVIE)
        }?.let {
            movie = it
        }
    }

    private fun launchFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_details_container, MovieDetailsFragment.newInstance(movie))
            .commit()
    }

    companion object {
        private const val EXTRA_MOVIE = "extra movie"
        fun newIntentMovieDetailsActivity(context: Context, movie: MovieDto): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }
}