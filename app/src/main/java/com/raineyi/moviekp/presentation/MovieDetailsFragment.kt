package com.raineyi.moviekp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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



    private fun parseParam() {
        val args = requireArguments()
        if(!args.containsKey(KEY)) {
            throw RuntimeException("Param movie is absent")
        }
        val movie = args.getString(KEY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {

        private const val KEY = "key"

        //movie: MovieDto
        fun newInstance(value: String): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, value)
                }
            }
        }
    }
}