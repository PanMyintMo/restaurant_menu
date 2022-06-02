package com.example.foodsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodsample.adapter.ReviewsAdapter
import com.example.foodsample.databinding.ActivityRestaurantMenuBinding
import com.example.foodsample.models.RestaurantDataModel
import com.example.foodsample.models.Reviews

class RestaurantMenuActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRestaurantMenuBinding
    private var reviews:List<Reviews?> ?= null
    private var adapter:ReviewsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val restaurantDataModel = intent.getParcelableExtra<RestaurantDataModel>("Restaurant")

        val actionBar = supportActionBar
        actionBar?.title = restaurantDataModel?.name
        actionBar?.subtitle = restaurantDataModel?.address
        actionBar?.setDisplayHomeAsUpEnabled(true)

        reviews = restaurantDataModel?.reviews
        initRecyclerView(reviews)
        binding.btnCheckOut.setOnClickListener {

        }
    }

    private fun initRecyclerView(reviews:List<Reviews?>?) {
        binding.recyclerMenuId.layoutManager=GridLayoutManager(this,2)
        adapter= ReviewsAdapter(reviews)
        binding.recyclerMenuId.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==  android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}