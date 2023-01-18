package com.example.kinopoisk.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kinopoisk.databinding.FragmentActorsBinding
import com.example.kinopoisk.presentation.viewmodels.ActorsViewModel

class ActorsFragment : Fragment() {

    private var _binding: FragmentActorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actorsViewModel =
            ViewModelProvider(this).get(ActorsViewModel::class.java)

        _binding = FragmentActorsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}