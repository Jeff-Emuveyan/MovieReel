package com.example.moviereel.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviereel.R
import com.example.moviereel.pojo.Movie
import com.example.moviereel.util.MovieListAdapter
import com.master.exoplayer.MasterExoPlayerHelper
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    lateinit var adapter: MovieListAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        val movieList = viewModel.getMovies()
        displayListOfMovies(movieList)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMovieButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addMovieFragment)
        }
    }


    private fun displayListOfMovies(listOfMovies: ArrayList<Movie>?){
        context?.let {
            recyclerView?.layoutManager = LinearLayoutManager(it)
            adapter = MovieListAdapter(it, recyclerView, listOfMovies)
            recyclerView.adapter = adapter

            val masterExoPlayerHelper = MasterExoPlayerHelper(context!!, id = R.id.exoPlayerView)
            masterExoPlayerHelper.makeLifeCycleAware((activity as AppCompatActivity))
            masterExoPlayerHelper.attachToRecyclerView(recyclerView)
            masterExoPlayerHelper.makeLifeCycleAware((activity as AppCompatActivity))
        }


    }

}
