package com.example.smdb.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smdb.Model.EndPoints
import com.example.smdb.Movies
import com.example.smdb.Result
import com.example.smdb.Model.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val request = ServiceBuilder.buildService(EndPoints::class.java)
    val key = "8bce9ec9952a3c292c2a37cd539e8464"
    var movielist = MutableLiveData<List<Result>>()
    var previous_name = ""

    fun getMovies(name: String) {

        if (previous_name !== name) {
            val call = request.getMovies(name, key)
            previous_name = name

            call.enqueue(object : Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {

                    movielist.postValue(response.body()?.results)

                }

                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    Log.d("Home Screen", t.toString())
                }

            })
        }
    }
}