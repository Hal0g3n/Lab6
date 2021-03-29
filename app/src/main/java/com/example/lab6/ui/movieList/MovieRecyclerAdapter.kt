package com.example.lab6.ui.movieList

import android.graphics.PixelFormat
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R
import com.example.lab6.model.MovieSDK

class MovieRecyclerAdapter(
    private val fragmentListener: MovieListFragment
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_card_layout,
                parent,
                false
            ),
            fragmentListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder

        val movie = MovieSDK.movies[position]
        fragmentListener.requireActivity().window.setFormat(PixelFormat.TRANSLUCENT)
        holder.itemVideo.setVideoURI(Uri.parse("android.resource://${fragmentListener.requireActivity().packageName}/${movie.trailer_id}"))
        holder.itemName.text = movie.name
    }

    override fun getItemCount(): Int {
        return MovieSDK.movies.size
    }

    class ViewHolder(
        itemView: View,
        listener: OnItemClickListener
    ): RecyclerView.ViewHolder(itemView) {

        val itemVideo: VideoView = itemView.findViewById(R.id.itemVideo)
        val itemName: TextView = itemView.findViewById(R.id.itemName)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, this)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, viewHolder: ViewHolder)
    }

}
