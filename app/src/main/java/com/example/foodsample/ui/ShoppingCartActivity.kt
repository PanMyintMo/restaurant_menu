package com.example.foodsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.foodsample.R
import com.example.foodsample.databinding.ActionBarNotificationIconBinding

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActionBarNotificationIconBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActionBarNotificationIconBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.title = "History"
        actionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(com.example.foodsample.R.menu.shopping_cart_menu, menu)
        val menuItem = menu?.findItem(com.example.foodsample.R.id.shopping_cart) as MenuItem
        val actionView = menuItem.actionView
        val badge = actionView.findViewById<TextView>(R.id.cart_badge)
        val textView = actionView.findViewById<ImageView>(R.id.cart_image)


        return super.onCreateOptionsMenu(menu)
    }
}