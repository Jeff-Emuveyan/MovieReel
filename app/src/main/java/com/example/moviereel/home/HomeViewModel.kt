package com.example.moviereel.home

import androidx.lifecycle.ViewModel
import com.example.moviereel.pojo.Movie
import com.example.moviereel.util.MovieItem

class HomeViewModel : ViewModel() {

    fun getMovies(): ArrayList<Movie>{
        val list =  ArrayList<Movie>()
        list.add(Movie("", "", "", 4, "", "http://62.138.24.130/tb/1/ea/frank_sinatra_my_way_with_lyrics_h264_75737.mp4"))
        list.add(Movie("", "", "", 4, "", "http://62.138.24.130/tb/1/ea/frank_sinatra_my_way_with_lyrics_h264_75737.mp4"))
        list.add(Movie("", "", "", 4, "", "http://62.138.24.130/tb/1/ea/frank_sinatra_my_way_with_lyrics_h264_75737.mp4"))
        list.add(Movie("", "", "", 4, "", "http://62.138.24.130/tb/1/ea/frank_sinatra_my_way_with_lyrics_h264_75737.mp4"))
        list.add(Movie("", "", "", 4, "", "http://62.138.24.130/tb/1/ea/frank_sinatra_my_way_with_lyrics_h264_75737.mp4"))
        return list
    }
}
