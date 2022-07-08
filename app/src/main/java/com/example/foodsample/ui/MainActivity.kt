package com.example.foodsample.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodsample.R
import com.example.foodsample.adapter.RestaurantAdapter
import com.example.foodsample.databinding.ActivityMainBinding
import com.example.foodsample.models.Restaurant
import com.example.foodsample.util.SpacingItemDecoration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import java.io.Writer

class MainActivity : BaseActivity(),
    RestaurantAdapter.OnItemClickedListener {
    private lateinit var binding: ActivityMainBinding
    private val restaurants = arrayListOf<Restaurant>()
    private lateinit var adapter: RestaurantAdapter

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

    override fun onItemClick( restaurant: Restaurant) {
        val intent = Intent(this, RestaurantMenuActivity::class.java)
        intent.putExtra(RestaurantMenuActivity.EXTRA_RESTAURANT, restaurant)
        startActivity(intent)
    }
}