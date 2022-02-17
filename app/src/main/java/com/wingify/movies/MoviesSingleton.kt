package com.wingify.movies

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MoviesSingleton  constructor(context: Context){
    companion object{
        @Volatile
        private var INSTANCE : MoviesSingleton?= null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MoviesSingleton(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    public fun <T> addToRequestQueue(request : Request<T>){
        requestQueue.add(request)
    }
}