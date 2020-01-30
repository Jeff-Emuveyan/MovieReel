package com.example.moviereel.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereel.R
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.master.exoplayer.MasterExoPlayer
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieItem(v: View): RecyclerView.ViewHolder(v) {

    lateinit var ivThumbNailUserImage: ImageView
    lateinit var tvUserName: TextView
    lateinit var tvDate: TextView
    lateinit var ratingsBar: RatingBar
    lateinit var progressBar: ProgressBar
    lateinit var ivPlayIcon: ImageView
    lateinit var ivThumbNailVideoImage: ImageView
        lateinit var exoPlayerView: MasterExoPlayer
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView

    lateinit var context: Context
    lateinit var view: View


    constructor(context: Context, v: View): this(v){
        this.context = context
        this.view = v

        ivThumbNailVideoImage = view.findViewById(R.id.ivVideoThumbNail)
        progressBar = view.findViewById(R.id.progressBar)
        ivPlayIcon = view.findViewById(R.id.imageView2)
        exoPlayerView = view.findViewById(R.id.exoPlayerView)
    }


}