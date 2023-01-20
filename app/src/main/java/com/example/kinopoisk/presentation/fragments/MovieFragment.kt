package com.example.kinopoisk.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.data.retrofit.model.RetrofitApiCallbackEntitiesMovies
import com.example.kinopoisk.databinding.FragmentMovieBinding
import com.example.kinopoisk.presentation.viewmodels.MovieViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieRecyclerViewAdapter: MovieAdapter
    private lateinit var movieRecyclerViewLayoutManager: LinearLayoutManager
    private lateinit var dialog: AlertDialog
    private lateinit var searchBar: SearchView
    private lateinit var page_forward: FloatingActionButton
    private lateinit var page_back: FloatingActionButton
    private var movieState: Parcelable? = null
    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieViewModel =
            ViewModelProvider(this).get(MovieViewModel::class.java)

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = SpotsDialog.Builder().setCancelable(true).setContext(context).setTheme(R.style.custom_spots_dialog).build()

        movieViewModel.initApi()

        movieRecyclerView = view.findViewById(R.id.search_movies_list_db)
        searchBar = view.findViewById(R.id.search_bar_movie_db)
        page_forward = view.findViewById(R.id.page_forward_movie_db)
        page_back= view.findViewById(R.id.page_back_movie)

        movieViewModel.livedata.observe(viewLifecycleOwner){
            if (movieRecyclerView.adapter == null) {
                movieRecyclerViewLayoutManager = LinearLayoutManager(context)
                movieRecyclerViewAdapter = MovieAdapter(it,
                    object : OnRecycleViewListener {
                        override fun onViewClick(Id: Int) {
                            val bundle = Bundle()
                            bundle.putInt("elementId", Id)
                            view.findNavController().navigate(R.id.action_navigation_home_to_movieelementFragment, bundle)
                            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).menu.findItem(R.id.navigation_actors).isEnabled = false
                            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).menu.findItem(R.id.navigation_favourites).isEnabled = false
                        }
                    })
                movieRecyclerView.layoutManager = movieRecyclerViewLayoutManager
                movieRecyclerView.adapter = movieRecyclerViewAdapter
                if (movieState != null) movieRecyclerViewLayoutManager.onRestoreInstanceState(movieState)
            } else {
                movieRecyclerViewAdapter.setRetrofitData(it)
            }

            if(it.docs.isEmpty()) Toast.makeText(context, resources.getString(R.string.search_null), Toast.LENGTH_SHORT).show()

            page = it.page

            if (page < it.pages && page > 1) {
                page_forward.isEnabled = true
                page_back.isEnabled = true
            } else if (page == 1 && it.pages > 1){
                page_forward.isEnabled = true
                page_back.isEnabled = false
            } else if (page == it.pages && it.pages > 1) {
                page_forward.isEnabled = false
                page_back.isEnabled = true
            } else {
                page_forward.isEnabled = false
                page_back.isEnabled = false
            }

            dialog.dismiss()
        }

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    searchBar.clearFocus()
                    page = 1
                    movieViewModel.getMoviesOrSeries(query, page)
                    dialog.show()
                } else {
                    Toast.makeText(context, resources.getString(R.string.search_bar_is_empty), Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

        page_forward.setOnClickListener {
            movieViewModel.getMoviesOrSeries(searchBar.query.toString(), page + 1)
            dialog.show()
        }

        page_back.setOnClickListener {
            movieViewModel.getMoviesOrSeries(searchBar.query.toString(), page - 1)
            dialog.show()
        }

    }

    override fun onPause() {
        super.onPause()
        movieState = movieRecyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieViewModel.livedata.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    class MovieAdapter(private val movies: RetrofitApiCallbackEntitiesMovies, private val listener: OnRecycleViewListener): RecyclerView.Adapter<MovieAdapter.MoviesViewHolder> (){

        private var moviesListAdapter: RetrofitApiCallbackEntitiesMovies = this.movies

        class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.name)
            val description: TextView = itemView.findViewById(R.id.description)
            val subDescription: TextView = itemView.findViewById(R.id.subDescription)
            val poster: ImageView = itemView.findViewById(R.id.image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return MoviesViewHolder(view)
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            holder.name.text = moviesListAdapter.docs[position].name + " (" + moviesListAdapter.docs[position].year.toString() + ')'
            holder.description.text = moviesListAdapter.docs[position].description
            holder.subDescription.text = moviesListAdapter.docs[position].rating.kp + " kp | " + moviesListAdapter.docs[position].rating.imdb + " imdb"
            try {
                if(moviesListAdapter.docs[position].poster.url != null) Picasso.get().load(moviesListAdapter.docs[position].poster.url).into(holder.poster)
                else holder.poster.setImageResource(R.drawable.no_poster)
            } catch (e: NullPointerException){
                holder.poster.setImageResource(R.drawable.no_poster)
            }
            holder.itemView.setOnClickListener {
                listener.onViewClick(moviesListAdapter.docs[position].id)
            }
        }

        override fun getItemCount(): Int {
            return moviesListAdapter.docs.size
        }

        fun setRetrofitData(movies: RetrofitApiCallbackEntitiesMovies){
            moviesListAdapter = movies
            notifyDataSetChanged()
        }
    }

    interface OnRecycleViewListener {
        fun onViewClick(Id: Int)
    }
}