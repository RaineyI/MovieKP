package com.raineyi.moviekp.presentation

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raineyi.moviekp.databinding.FragmentMovieDetailsBinding
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import com.squareup.picasso.Picasso

class MovieDetailsFragment : Fragment() {

    private var value: String = ""

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetailsBinding == null")

    private lateinit var movie: Movie
    private lateinit var description: Description

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        binding.backArrow.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
            activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupViews() {
        Picasso.get()
            .load(movie.posterUrl)
            .into(binding.imBanner)

        binding.tvName.text = movie.name
        binding.tvDescription.text = description.description
        binding.tvGenres.text = movie.genres
        binding.tvCountry.text = movie.countries
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_MOVIE)) {
            throw RuntimeException("Param movie is absent")
        }
        when {
            SDK_INT >= 33 -> args.getParcelable(
                EXTRA_MOVIE,
                Movie::class.java
            )

            else -> args.getParcelable<Movie>(EXTRA_MOVIE)
        }?.let {
            movie = it
        }

        if (!args.containsKey(EXTRA_DESCRIPTION)) {
            throw RuntimeException("Param description is absent")
        }
        when {
            SDK_INT >= 33 -> args.getParcelable(
                EXTRA_DESCRIPTION,
                Description::class.java
            )

            else -> args.getParcelable<Description>(EXTRA_DESCRIPTION)
        }?.let {
            description = it
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTRA_MOVIE = "extra_movie"
        private const val EXTRA_DESCRIPTION = "extra_description"

        fun newInstance(movie: Movie, description: Description): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_MOVIE, movie)
                    putParcelable(EXTRA_DESCRIPTION, description)
                }
            }
        }
    }
}