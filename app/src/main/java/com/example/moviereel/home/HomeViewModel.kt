package com.example.moviereel.home

import androidx.lifecycle.ViewModel
import com.example.moviereel.pojo.Movie
import com.example.moviereel.util.MovieItem

class HomeViewModel : ViewModel() {

    fun getMovies(): ArrayList<Movie>{
        val list =  ArrayList<Movie>()
        list.add(Movie("", "", "", 4, "", "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        list.add(Movie("", "", "", 4, "", "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        list.add(Movie("", "", "", 4, "", "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        list.add(Movie("", "", "", 4, "", "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        list.add(Movie("", "", "", 4, "", "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        return list
    }
}
