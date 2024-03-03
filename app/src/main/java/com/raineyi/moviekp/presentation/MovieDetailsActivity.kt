package com.raineyi.moviekp.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
import java.lang.RuntimeException

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
                KEY_MOVIE,
                MovieDto::class.java
            )
            else -> intent.getParcelableExtra<MovieDto>(KEY_MOVIE)
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

        private const val KEY_MOVIE = "movie_key"
        fun newIntentMovieDetailsActivity(context: Context, movie: MovieDto): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(KEY_MOVIE, movie)
            return intent
        }
    }
}