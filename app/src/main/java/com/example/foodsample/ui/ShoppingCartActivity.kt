package com.example.foodsample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodsample.adapter.MenuRecordAdapter
import com.example.foodsample.databinding.ActionBarNotificationIconBinding
import com.example.foodsample.models.Menu
import com.example.foodsample.models.RestaurantDataModel


class ShoppingCartActivity : AppCompatActivity() {
    private var newMenuItem: MutableList<Menu?> = mutableListOf()
    private var menuRecordAdapter: MenuRecordAdapter? = null

    private var restaurantModel: RestaurantDataModel? = null

    private lateinit var binding: ActionBarNotificationIconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActionBarNotificationIconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantModel = intent?.getParcelableExtra("menu_data_added")
        newMenuItem = restaurantModel?.menus as  MutableList<Menu?>
        val actionBar = supportActionBar
        actionBar?.title = "Basket List"
        actionBar?.subtitle = "Address " + restaurantModel?.address
        actionBar?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerRecord.layoutManager = LinearLayoutManager(this)
        menuRecordAdapter= MenuRecordAdapter(this,newMenuItem)
        binding.recyclerRecord.adapter = menuRecordAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        val intent= Intent(this@ShoppingCartActivity,RestaurantMenuActivity::class.java)

        intent.putExtra("remainingItem",newMenuItem.size)
        startActivity(intent)
        finish()
    }

}



