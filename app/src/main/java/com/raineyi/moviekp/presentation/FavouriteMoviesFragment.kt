package com.raineyi.moviekp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raineyi.moviekp.R
import com.raineyi.moviekp.databinding.FragmentFavouriteMoviesBinding
import com.raineyi.moviekp.databinding.FragmentPopularMoviesBinding
import java.lang.RuntimeException

class FavouriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavouriteMoviesBinding? = null
    private val binding: FragmentFavouriteMoviesBinding
        get() = _binding ?: throw RuntimeException("FragmentPopularMoviesBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): FavouriteMoviesFragment {
            return FavouriteMoviesFragment()
        }
    }
}