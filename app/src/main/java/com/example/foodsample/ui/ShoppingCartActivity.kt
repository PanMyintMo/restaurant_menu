package com.example.foodsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.example.foodsample.databinding.ActionBarNotificationIconBinding
import com.example.foodsample.models.RestaurantDataModel

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActionBarNotificationIconBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActionBarNotificationIconBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val menuNoti=intent.getParcelableExtra<RestaurantDataModel>("menu_data_added")

        val actionBar = supportActionBar
        actionBar?.title = menuNoti?.name
        actionBar?.subtitle = menuNoti?.address
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}