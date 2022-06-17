package com.example.foodsample.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodsample.R
import com.example.foodsample.R.id.shopping_cart
import com.example.foodsample.adapter.MenuListAdapter
import com.example.foodsample.databinding.ActivityRestaurantMenuBinding
import com.example.foodsample.entity.MenuEntity
import com.example.foodsample.models.Menu
import com.example.foodsample.models.RestaurantDataModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import database.MenuDatabase
import kotlinx.coroutines.launch

class RestaurantMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {
    private lateinit var binding: ActivityRestaurantMenuBinding
    private val menuDatabase by lazy {
        MenuDatabase.getDatabase(this).DaoMenu()
    }

    private lateinit var restaurantModel: RestaurantDataModel
    private var menuList: List<Menu?>? = null
    private var menuListAdapter: MenuListAdapter? = null
    private var itemsInTheCartList: MutableList<Menu?>? = null
    private var totalItemInCartCount = 0


    private lateinit var newAddMenu: MutableList<Menu>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        restaurantModel = intent?.getParcelableExtra<RestaurantDataModel>("Restaurant")!!

        val actionBar = supportActionBar
        actionBar?.title = restaurantModel.name
        actionBar?.subtitle = restaurantModel.address
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuList = restaurantModel.menus
        initRecyclerView(menuList)
        observerMenuData()
        binding.checkoutButton.setOnClickListener {
            if (itemsInTheCartList != null && itemsInTheCartList!!.size <= 0) {
                Toast.makeText(
                    this@RestaurantMenuActivity,
                    "Please add some items in cart",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                restaurantModel.menus = itemsInTheCartList
                val intent = Intent(this@RestaurantMenuActivity, PlaceYourOrderActivity::class.java)
                intent.putExtra("RestaurantModel", restaurantModel)
                resultLauncher.launch(intent)
            }
        }
    }

    private fun observerMenuData() {
        // newAddMenu = restaurantModel.menus as MutableList<Menu>
        lifecycleScope.launch {
            menuDatabase.getAllData().collect { menuDataList ->
                if (menuDataList.isNotEmpty()) {
                    Log.d("Data", "You get data from database")
                }
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                finish()
            }
        }

    private fun initRecyclerView(menu: List<Menu?>?) {
        binding.menuRecyclerView.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menu, this)
        binding.menuRecyclerView.adapter = menuListAdapter
    }

    override fun addToCartClickListener(custmenu: Menu) {
        if (itemsInTheCartList == null) {
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(custmenu)
        totalItemInCartCount++
        updateCarts()
        binding.checkoutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
    }

    override fun updateCartClickListener(isAdding: Boolean, custmenu: Menu) {
        if (isAdding) {
            itemsInTheCartList?.add(custmenu)
            totalItemInCartCount++
        } else {
            val index = itemsInTheCartList!!.indexOf(custmenu)
            itemsInTheCartList?.removeAt(index)
            totalItemInCartCount--
        }
        updateCarts()
        binding.checkoutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
    }

    override fun removeFromCartClickListener(custmenu: Menu) {
        if (itemsInTheCartList!!.contains(custmenu)) {
            itemsInTheCartList?.remove(custmenu)
            totalItemInCartCount--
            updateCarts()
            binding.checkoutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
        }
    }

    override fun onCreateOptionsMenu(menus: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.shopping_cart_menu, menus)
        updateCarts()
        return super.onCreateOptionsMenu(menus)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish() // action bar back button
            }
            R.id.shopping_cart -> {
                saveToDatabase(restaurantModel)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToDatabase(restaurantDataModel: RestaurantDataModel) {
        lifecycleScope.launch {
            for (m in itemsInTheCartList!!.indices) {
                menuDatabase.addMenu(
                    MenuEntity(
                        id = 0,
                        name = itemsInTheCartList?.get(m)?.name.toString(), itemsInTheCartList?.get(m)?.price!!.toDouble()
                    )
                )
            }
        }
        val intent = Intent(this, ShoppingCartActivity::class.java)
        intent.putExtra("menu_data_added", restaurantDataModel)
        setResult(Activity.RESULT_OK, intent)
        //     readMenulauncher.launch(intent)
        startActivity(intent)
    }

    private fun updateCarts() {
        val badge = BadgeDrawable.create(this)
        if (totalItemInCartCount == 0) {
            BadgeUtils.detachBadgeDrawable(badge, binding.toolBar, shopping_cart)
        } else {
            //    badge.badgeTextColor = ContextCompat.getColor(this, R.color.purple_200)
            badge.number = totalItemInCartCount
            BadgeUtils.attachBadgeDrawable(badge, binding.toolBar, shopping_cart)
        }
    }
}


