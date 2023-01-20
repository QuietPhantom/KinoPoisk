package com.example.kinopoisk.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.businesslogic.entities.MovieEntity
import com.example.kinopoisk.data.Application
import com.example.kinopoisk.databinding.FragmentFavouriteBinding
import com.example.kinopoisk.presentation.viewmodels.FavouritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var favouritesRecyclerViewAdapter: MovieAdapter
    private lateinit var favouritesRecyclerViewLayoutManager: LinearLayoutManager
    private lateinit var searchBar: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouritesViewModel =
            ViewModelProvider(this).get(FavouritesViewModel::class.java)

        favouritesViewModel.init(requireActivity().application as Application)

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritesRecyclerView = view.findViewById(R.id.movies_list_db)
        searchBar = view.findViewById(R.id.search_bar_movie_db)


        favouritesViewModel.getTitles().observe(viewLifecycleOwner){
            favouritesRecyclerViewLayoutManager = LinearLayoutManager(context)
            favouritesRecyclerViewAdapter =
                MovieAdapter(it,
                    object : OnGridRecycleViewListener {
                        override fun onViewClick(Id: Int) {
                            val bundle = Bundle()
                            bundle.putInt("elementId", Id)
                            view.findNavController().navigate(R.id.action_navigation_favourites_to_movieelementFragment, bundle)
                            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).menu.findItem(R.id.navigation_movies_or_serials).isEnabled = false
                            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).menu.findItem(R.id.navigation_actors).isEnabled = false
                        }
                    })
            favouritesRecyclerView.layoutManager = favouritesRecyclerViewLayoutManager
            favouritesRecyclerView.adapter = favouritesRecyclerViewAdapter

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    favouritesRecyclerViewAdapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    favouritesRecyclerViewAdapter.filter.filter(query)
                    return false
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).removeBadge(R.id.navigation_favourites)
    }

    override fun onPause() {
        super.onPause()
        searchBar.setQuery("", false);
        searchBar.clearFocus()
        searchBar.isIconified = true;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        favouritesViewModel.getTitles().removeObservers(viewLifecycleOwner)
        _binding = null
    }

    class MovieAdapter(private val MoviesList: List<MovieEntity>, private val listener: OnGridRecycleViewListener): RecyclerView.Adapter<MovieAdapter.GridTitlesViewHolder>(),
        Filterable {

        private var moviesListSearch: List<MovieEntity> = this.MoviesList

        class GridTitlesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val name: TextView = itemView.findViewById(R.id.name)
            val description: TextView = itemView.findViewById(R.id.description)
            val subDescription: TextView = itemView.findViewById(R.id.subDescription)
            val poster: ImageView = itemView.findViewById(R.id.image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridTitlesViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false)

            return GridTitlesViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: GridTitlesViewHolder, position: Int) {
            holder.name.text = moviesListSearch[position].name + " (" + moviesListSearch[position].year.toString() + ')'
            holder.description.text = moviesListSearch[position].description
            holder.subDescription.text = moviesListSearch[position].raiting_kp + " kp | " + moviesListSearch[position].raiting_imdb + " imdb"
            try {
                if(moviesListSearch[position].poster != null) Picasso.get().load(moviesListSearch[position].poster).into(holder.poster)
                else holder.poster.setImageResource(R.drawable.no_poster)
            } catch (e: NullPointerException){
                holder.poster.setImageResource(R.drawable.no_poster)
            }
            holder.itemView.setOnClickListener {
                listener.onViewClick(moviesListSearch[position].id)
            }
        }

        override fun getItemCount(): Int {
            return moviesListSearch.size
        }

        override fun getFilter(): Filter {
            return object : Filter(){
                override fun performFiltering(searchChars: CharSequence?): FilterResults {
                    val searchString = searchChars.toString()
                    if(searchString.isEmpty()){
                        moviesListSearch = MoviesList as MutableList<MovieEntity>
                    } else {
                        val resultList: MutableList<MovieEntity> = mutableListOf()
                        for (row in MoviesList){
                            if (row.name.lowercase().contains(searchString.lowercase())) resultList.add(row)
                        }
                        moviesListSearch = resultList
                    }
                    val filterResults = Filter.FilterResults()
                    filterResults.values = moviesListSearch
                    return filterResults
                }

                override fun publishResults(charSequence: CharSequence?, filterResult: FilterResults?) {
                    moviesListSearch = filterResult!!.values as List<MovieEntity>
                    notifyDataSetChanged()
                }
            }
        }

        fun getTitlesList(): List<MovieEntity>{
            return moviesListSearch
        }
    }

    interface OnGridRecycleViewListener {
        fun onViewClick(Id: Int)
    }
}