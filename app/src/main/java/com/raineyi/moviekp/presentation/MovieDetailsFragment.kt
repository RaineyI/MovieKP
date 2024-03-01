package com.raineyi.moviekp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raineyi.moviekp.R
import com.raineyi.moviekp.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
//
//    private var _binding: FragmentMovieDetailsBinding? = null
//    private val binding: FragmentMovieDetailsBinding = _binding ?:
//        throw RuntimeException("FragmentMovieDetailsBinding == null")

//    private lateinit var viewModel: MovieFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
//        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
//        return binding.root
    }


//    companion object {
//        fun newInstance(): MovieDetailsFragment {
//            return MovieDetailsFragment()
//        }
//    }
}