package com.example.kinopoisk.presentation.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.presentation.viewmodels.MovieElementViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.businesslogic.entities.MovieEntity
import com.example.kinopoisk.data.Application
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.launch

class MovieElementFragment : Fragment() {

    companion object {
        fun newInstance() = MovieElementFragment()
    }

    private lateinit var MovieViewModel: MovieElementViewModel
    private var MovieId: Int = 1
    private lateinit var MovieSaveInformation: MovieEntity
    private lateinit var MovieName: TextView
    private lateinit var Genre: TextView
    private lateinit var Dates: TextView
    private lateinit var Rating: TextView
    private lateinit var AgeRating: TextView
    private lateinit var Count: TextView
    private lateinit var StatusAndType: TextView
    private lateinit var FullDescription: ExpandableTextView
    private lateinit var MoviePoster: ImageView
    private lateinit var AddMovie: Button
    private lateinit var DeleteMovie: Button
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MovieViewModel = ViewModelProvider(this).get(MovieElementViewModel::class.java)
        MovieId = requireArguments().getInt("elementId")
        return inflater.inflate(R.layout.fragment_movie_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = SpotsDialog.Builder().setCancelable(true).setContext(context).setTheme(R.style.custom_spots_dialog).build()

        MovieName = view.findViewById(R.id.MovieName)
        Genre = view.findViewById(R.id.slogan)
        Dates = view.findViewById(R.id.Dates)
        Rating = view.findViewById(R.id.Rating)
        AgeRating = view.findViewById(R.id.ageRating)
        Count = view.findViewById(R.id.Count)
        StatusAndType = view.findViewById(R.id.StatusAndType)
        FullDescription = view.findViewById(R.id.expand_text_view)
        MoviePoster = view.findViewById(R.id.movieImage)
        AddMovie = view.findViewById(R.id.addMovie)
        DeleteMovie = view.findViewById(R.id.deleteMovie)

        MovieViewModel.initApi(requireActivity().application as Application)

        MovieViewModel.getCountMovieById(MovieId).observe(viewLifecycleOwner){
            if(it == 1) AddMovie.isEnabled = false
            else DeleteMovie.isEnabled = false
        }

        MovieViewModel.livedata.observe(viewLifecycleOwner){
            Picasso.get().load(it.poster.url).into(MoviePoster)

            var summaryGenres: String = ""
            for (genre in 0 until it.genres.size){
                summaryGenres += if (genre == it.genres.size-1) "${it.genres[genre].name} "
                else "${it.genres[genre].name}/ "
            }
            Genre.text = summaryGenres

            MovieName.text = it.name + " (" + it.year + ") "
            Dates.text = it.createDate.substringBefore('T')
            Rating.text = it.rating.kp + " kp | " + it.rating.imdb + " imdb | " + it.rating.await + " await | " + it.rating.filmCritics + " FM "
            AgeRating.text = it.ratingMpaa + " (" +  it.ageRating + "+)"
            Count.text = it.fees.world.value + it.fees.world.currency
            StatusAndType.text = it.status + " " + it.type
            FullDescription.text = it.description
            MovieSaveInformation = MovieEntity(it.id, it.name, it.year, it.description, it.rating.kp, it.rating.imdb, it.poster.url)
            dialog.dismiss()
        }

        MovieViewModel.getMovieById(MovieId)
        dialog.show()

        AddMovie.setOnClickListener{
            MovieViewModel.viewModelScope.launch {
                MovieViewModel.saveMovie(MovieSaveInformation)
                AddMovie.isEnabled = false
                DeleteMovie.isEnabled = true
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).getOrCreateBadge(R.id.navigation_favourites).number += 1
            }
        }

        DeleteMovie.setOnClickListener{
            MovieViewModel.viewModelScope.launch {
                MovieViewModel.deleteMovie(MovieSaveInformation)
                AddMovie.isEnabled = true
                DeleteMovie.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MovieViewModel.livedata.removeObservers(viewLifecycleOwner)
        MovieViewModel.getCountMovieById(MovieId).removeObservers(viewLifecycleOwner)
    }
}