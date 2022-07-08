package com.example.foodsample.ui

import android.content.Intent
import android.os.Bundle
import com.example.foodsample.R
import com.example.foodsample.adapter.CartItemAdapter
import com.example.foodsample.databinding.ActivityShoppingCartBinding
import com.example.foodsample.entity.CartItem
import com.example.foodsample.models.Restaurant
import com.example.foodsample.util.SpacingItemDecoration
import database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingCartActivity : BaseActivity(), CartItemAdapter.OnCartUpdateListener {

    companion object {
        const val MAX_ITEM_COUNT = 10
    }

    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var restaurant: Restaurant
    private lateinit var appDatabase: AppDatabase
    private lateinit var cartItems: ArrayList<CartItem>
    private lateinit var adapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)
        intent.getParcelableExtra<Restaurant>(RestaurantMenuActivity.EXTRA_RESTAURANT)?.let {
            restaurant = it
            loadCartItems()
        }
    }

    private fun loadCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            cartItems = appDatabase.cartItemDao().getAllFromRestaurant(restaurant.name) as ArrayList<CartItem>
            launch(Dispatchers.Main) { initViews() }
        }
    }
    private fun initViews() {
        val decoration = SpacingItemDecoration(1, toPx(15), true)
        binding.recycler.addItemDecoration(decoration)
        binding.recycler.setHasFixedSize(true)

        adapter = CartItemAdapter(this, cartItems, this)
        binding.recycler.adapter = adapter

        binding.btnOrder.setOnClickListener {
            val intent = Intent(this, OrderNowActivity::class.java)
            intent.putExtra(RestaurantMenuActivity.EXTRA_RESTAURANT, restaurant)
            startActivity(intent)
        }
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        binding.btnOrder.isEnabled = cartItems.isNotEmpty()

        if (cartItems.isNotEmpty()) {
            var totalPrice = 0.0
            for (cartItem in cartItems) totalPrice += cartItem.price * cartItem.count
            binding.btnOrder.text =
                getString(R.string.btn_order_with_unit, totalPrice)
        }
    }
    override fun onCartUpdated(position: Int, cartItem: CartItem) {
        cartItems[position] = cartItem
        updateTotalPrice()

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.cartItemDao().update(cartItems[position])
        }
    }

    override fun onCartRemoved(position: Int, cartItem: CartItem) {
        cartItems.removeAt(position)
        adapter.notifyItemRemoved(position)
        updateTotalPrice()

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.cartItemDao().delete(cartItem)
        }
    }
    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}