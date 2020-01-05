package com.example.moviereel.util

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereel.R
import com.example.moviereel.pojo.Movie
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.movie_item.view.*

typealias couldNotPlayVideo = (String) -> Unit

class MovieListAdapter(): RecyclerView.Adapter<MovieItem>() {

    var context: Context? = null
    var recyclerView: RecyclerView? = null
    var movieList: ArrayList<Movie>? = null

    constructor(context: Context, recyclerView: RecyclerView, movieList: ArrayList<Movie>?) : this() {

        this.context = context
        this.recyclerView = recyclerView
        this.movieList = movieList

        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemsCount = linearLayoutManager!!.childCount//total number of visible items presently on the screen.
                val totalItemCount = linearLayoutManager.itemCount//total number of items (visible and invisible).
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()//visible item at the top of the screen.

                //Log.e("MovieListAdapter", "number of visible items: $visibleItemsCount  totalItemCount: $totalItemCount  firstVisibleItemPosition: $firstVisibleItemPosition")

                if(firstVisibleItemPosition == 1){
                    val linearLayout = linearLayoutManager.getChildAt(1) as LinearLayout
                    val cardView = linearLayout.getChildAt(0) as CardView
                    val constraintLayout = cardView.getChildAt(0) as ConstraintLayout
                    for(child in constraintLayout.children){
                        if(child is ProgressBar && child.id == R.id.progressBar){
                            child.visibility = View.VISIBLE
                            Log.e("MovieListAdapter", "number of visible items: $visibleItemsCount  totalItemCount: $totalItemCount  firstVisibleItemPosition: $firstVisibleItemPosition")
                        }
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItem {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return MovieItem(context!!, view)
    }


    override fun onBindViewHolder(holder: MovieItem, position: Int) {

        fun playVideoUIState(show: Boolean){
            if(show){
                holder.progressBar.visibility = View.VISIBLE
                holder.ivPlayIcon.visibility = View.INVISIBLE
                holder.exoPlayerView.visibility = View.VISIBLE
            }else{
                holder.progressBar.visibility = View.INVISIBLE
                holder.ivPlayIcon.visibility = View.VISIBLE
                holder.ivThumbNailVideoImage.visibility = View.VISIBLE
                holder.exoPlayerView.visibility = View.INVISIBLE
            }
        }

        fun playVideo(link: String, couldNotPlayVideo: couldNotPlayVideo){
            playVideoUIState(true)
            MovieListAdapter.positionOfItemPlayingVideo = position
            try {
                //We use exoplayer so that our videos load faster.
                val bandwidthMeter = DefaultBandwidthMeter()
                val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
                val exoPlayer = ExoPlayerFactory.newSimpleInstance(context!!, trackSelector)

                val uri = Uri.parse(link)
                //val uri = Uri.parse("android.resource://${context!!.packageName}/${R.raw.james}")
                //val uri = Uri.parse("assets://abba.3gp")

                val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
                //val dataSourceFactory = DefaultDataSourceFactory(context, "exoplayer_video")
                val extractorsFactory = DefaultExtractorsFactory()
                val mediaSource = ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)

                holder.exoPlayerView.setPlayer(exoPlayer)
                exoPlayer.prepare(mediaSource)

                exoPlayer.addListener(object : Player.EventListener {
                    fun onTimelineChanged(timeline: Timeline, manifest: Any) {}

                    override fun onLoadingChanged(isLoading: Boolean) {}

                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                        if (playbackState == ExoPlayer.STATE_BUFFERING) {
                            holder.progressBar.visibility = View.VISIBLE
                        } else {
                            holder.ivThumbNailVideoImage.visibility = View.INVISIBLE
                            holder.progressBar.visibility = View.INVISIBLE
                        }

                        if (playbackState == ExoPlayer.STATE_ENDED) {
                            exoPlayer.seekTo(0) //if the video has finished playing, restart it.
                        }
                    }

                    override fun onPlayerError(error: ExoPlaybackException) {
                        couldNotPlayVideo.invoke(if(error.message != null) error.message!! else "Something went wrong")
                    }

                    fun onPositionDiscontinuity() {}
                })

                exoPlayer.setPlayWhenReady(true)

            } catch (e: Exception) {
                couldNotPlayVideo.invoke(if(e.message != null) e.message!! else "Something went wrong")
            }
        }

        if(movieList != null){
            val movie = movieList!![position]

            holder.ivThumbNailVideoImage.setOnClickListener{
                //clicking on the thumbNail starts the video loading
                playVideo(movie.videoLink, couldNotPlayVideo = {
                    playVideoUIState(false)
                    showAlert("Error", it, context!!)
                })
            }
        }
    }


    private fun showAlert(title: String, message: String, context: Context){

        val alert = AlertDialog.Builder(context)
        alert.setTitle(title)
        alert.setMessage(message)
        alert.show()
    }
    
    
    companion object{
        /**
         * Gives you the position of the item in the recycler view who is currently streaming a video or preparing to do so.
         **/
        var positionOfItemPlayingVideo: Int? = null
    }

}