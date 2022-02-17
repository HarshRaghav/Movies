package com.wingify.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesAdapter(private val listener : MovieClicked): RecyclerView.Adapter<MoviesViewHolder>(){

    private val movieList : ArrayList<MovieItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item,parent ,false)
        val viewHolder = MoviesViewHolder(view)
        view.setOnClickListener {
            listener.onMovieClicked(movieList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentMovie  = movieList.get(position)
        holder.title.text = currentMovie.title
        holder.year.text  = currentMovie.year
        Glide.with(holder.itemView.context).load(currentMovie.Image).into(holder.image)
    }

    fun updateMovies(list: ArrayList<MovieItem>){
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }
}

class MoviesViewHolder(item : View) : RecyclerView.ViewHolder(item){
    val title : TextView = item.findViewById(R.id.movies_list_item_title)
    val image : ImageView = item.findViewById(R.id.movies_list_item_image)
    val year  : TextView = item.findViewById(R.id.movies_list_item_year)
}
interface MovieClicked{
    fun onMovieClicked(item : MovieItem)
}