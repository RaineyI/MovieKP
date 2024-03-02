package com.raineyi.moviekp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raineyi.moviekp.R

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportFragmentManager.beginTransaction()
            .add(R.id.movie_details_container, MovieDetailsFragment.newInstance("value"))
            .commit()
    }

    companion object {
        fun newIntentMovieDetailsActivity(context: Context): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            return intent
        }
    }
}