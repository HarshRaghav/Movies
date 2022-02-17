package com.wingify.movies

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity() ,MovieClicked{
    lateinit var moviesAdapter : MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val movieRecyclerView : RecyclerView= findViewById(R.id.movie_list_recycler_view)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        //GlobalScope.launch {
            getMovies()
        //}

        moviesAdapter = MoviesAdapter(this)
        movieRecyclerView.adapter = moviesAdapter
    }

     private fun getMovies(){
        val movieSearchURL ="http://www.omdbapi.com/?apikey=3c76c7e7&s=spider&page=1"
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            movieSearchURL,
            null,
            Response.Listener {
                val moviesJSONArray = it.getJSONArray("Search")
                val moviesArray = ArrayList<MovieItem>()
                for(i in 0 until moviesJSONArray.length()){
                    val moviesJsonObject = moviesJSONArray.getJSONObject(i)
                    val movieItem = MovieItem(
                        moviesJsonObject.getString("Title"),
                        moviesJsonObject.getString("Poster"),
                        moviesJsonObject.getString("Year")
                    )
                    moviesArray.add(movieItem)
                }
                moviesAdapter.updateMovies(moviesArray)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something Went Wrong in Fetching Data",Toast.LENGTH_LONG).show()
            }
        )
        {
            @Throws(AuthFailureError :: class)
            override fun getParams(): Map<String, String> {
                val params :MutableMap<String,String> = HashMap()
                params["apikey"] = "3c76c7e7"
                params["s"] = "spider"
                params["page"] = "1"
                return params
            }
        }
         MoviesSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onMovieClicked(item: MovieItem) {
       // val intent  = Intent(this,MovieActivity)
    }
}