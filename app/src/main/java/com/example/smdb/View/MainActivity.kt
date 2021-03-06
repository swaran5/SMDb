package com.example.smdb.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smdb.ViewModel.MainViewModel
import com.example.smdb.R
import com.example.smdb.ViewModel.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val category = arrayOf("Popular", "Top Rated", "Now Playing", "Up Coming")
        select_category.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category)

        select_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var name = ""
                when (category[position]) {

                    "Popular" -> name = "popular"
                    "Top Rated" -> name = "top_rated"
                    "Now Playing" -> name = "now_playing"
                    "Up Coming" -> name = "upcoming"
                }
                viewModel.getMovies(name)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        viewModel.movielist.observe(this@MainActivity, {
            val adapter = MyAdapter(this@MainActivity, it)
            recyclerView.adapter = adapter
        })
    }
}