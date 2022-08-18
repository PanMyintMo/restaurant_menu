package com.example.foodsample.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodsample.R
import com.example.foodsample.adapter.RestaurantAdapter
import com.example.foodsample.databinding.ActivityMainBinding
import com.example.foodsample.models.Restaurant
import com.example.foodsample.util.SpacingItemDecoration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : BaseActivity(),
    RestaurantAdapter.OnItemClickedListener {
    private lateinit var binding: ActivityMainBinding
    private val restaurants = arrayListOf<Restaurant>()
    private lateinit var adapter: RestaurantAdapter
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.title = "Restaurant List"
        initRecyclerView()
        restaurants.addAll(getRestaurants())
        adapter.notifyItemRangeInserted(0, restaurants.size)

    }

    private fun initRecyclerView() {
        val decoration = SpacingItemDecoration(1, toPx(15), true)
        binding.recyclerRestaurant.addItemDecoration(decoration)
        binding.recyclerRestaurant.setHasFixedSize(true)
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(this)
        adapter = RestaurantAdapter(restaurants, this)
        binding.recyclerRestaurant.adapter = adapter

    }

    private fun getRestaurants(): List<Restaurant> {
        val json = assets.open("restaurant.json")
            .bufferedReader()
            .use { it.readText() }
        val restaurantListType = object : TypeToken<List<Restaurant>>() {}.type
        return Gson().fromJson(json, restaurantListType) as List<Restaurant>
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.restaurant_search, menu)
        val search = menu?.findItem(R.id.rest_search)
        searchView = search?.actionView as? SearchView


        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        return  super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(restaurant: Restaurant) {

        /* val intent = Intent(this@MainActivity, DetailActivity::class.java)
         intent.putExtra(RestaurantMenuActivity.EXTRA_RESTAURANT, restaurants)
         startActivity(intent)*/

    }
}