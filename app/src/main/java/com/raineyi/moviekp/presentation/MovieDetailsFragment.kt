package com.raineyi.moviekp.presentation

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.raineyi.moviekp.R
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.databinding.FragmentMovieDetailsBinding
import java.lang.RuntimeException

class MovieDetailsFragment : Fragment() {

    private var value: String = ""

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetailsBinding == null")

//    private lateinit var viewModel: MovieFragmentViewModel

    private lateinit var movie: MovieDto


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
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
        settingUpViews()
        binding.backArrow.setOnClickListener{
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun settingUpViews() {
        Glide.with(this)
            .load(movie.posterUrl)
            .into(binding.imBanner)
        binding.tvName.text = movie.name.toString()
        //       binding.tvDescription.text =
        binding.tvGenre.text =  movie.genres?.get(0)?.genre?.replaceFirstChar { it.uppercase() }
        binding.tvCountry.text = movie.countries?.get(0)?.country?.replaceFirstChar { it.uppercase() }
    }

    private fun parseParam() {
        val args = requireArguments()
        if(!args.containsKey(KEY_MOVIE)) {
            throw RuntimeException("Param movie is absent")
        }
        when {
            SDK_INT >= 33 -> args.getParcelable(
                KEY_MOVIE,
                MovieDto::class.java
            )
            else -> args.getParcelable<MovieDto>(KEY_MOVIE)
        }?.let {
            movie = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val KEY_MOVIE = "movie_key"

        fun newInstance(movie: MovieDto): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MOVIE, movie)
                }
            }
        }
    }
}