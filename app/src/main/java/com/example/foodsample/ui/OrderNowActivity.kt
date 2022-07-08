package com.example.foodsample.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import com.example.foodsample.R
import com.example.foodsample.adapter.CartItemAdapter
import com.example.foodsample.databinding.ActivityOrderNowBinding
import com.example.foodsample.entity.CartItem
import com.example.foodsample.models.Restaurant
import com.example.foodsample.util.SpacingItemDecoration
import com.google.android.material.textfield.TextInputLayout
import database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderNowActivity : BaseActivity() {

    private lateinit var binding: ActivityOrderNowBinding
    private lateinit var restaurant: Restaurant
    private lateinit var appDatabase: AppDatabase
    private lateinit var cartItems: ArrayList<CartItem>
    private lateinit var adapter: CartItemAdapter
    private var subtotalPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)
        intent.getParcelableExtra<Restaurant>(RestaurantMenuActivity.EXTRA_RESTAURANT)?.let {
            restaurant = it
            loadCartItems()
        }
    }

    private fun loadCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            cartItems = appDatabase.cartItemDao()
                .getAllFromRestaurant(restaurant.name) as ArrayList<CartItem>
            launch(Dispatchers.Main) { initViews() }
        }
    }

    private fun initViews() {
        val decoration = SpacingItemDecoration(1, toPx(15), true)
        binding.recycler.addItemDecoration(decoration)
        binding.recycler.setHasFixedSize(true)
        adapter = CartItemAdapter(this, cartItems, null)
        binding.recycler.adapter = adapter

        binding.edtName.addTextChangedListener { binding.edtNameLayout.error = null }
        binding.edtAddress.addTextChangedListener { binding.edtAddressLayout.error = null }
        binding.edtCity.addTextChangedListener { binding.edtCityLayout.error = null }
        binding.edtState.addTextChangedListener { binding.edtNameLayout.error = null }
        binding.edtZip.addTextChangedListener { binding.edtZipLayout.error = null }
        binding.edtCardNumber.addTextChangedListener { binding.edtCardNumberLayout.error = null }
        binding.edtCardExpiry.addTextChangedListener { binding.edtCardExpiryLayout.error = null }
        binding.edtCardPin.addTextChangedListener { binding.edtCardPinLayout.error = null }

        for (cartItem in cartItems) {
            subtotalPrice += cartItem.price * cartItem.count
        }

        binding.tvSubtotal.text = getString(R.string.title_price_unit, subtotalPrice)
        binding.tvDeliveryCharge.text =
            getString(R.string.title_price_unit, restaurant.deliveryCharge)
        updateTotalPrice(false)

        binding.switchDelivery.setOnCheckedChangeListener { _, isChecked ->
            binding.deliveryLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
            updateTotalPrice(isChecked)
        }

        binding.btnOrderNow.setOnClickListener { checkForm() }
    }

    private fun updateTotalPrice(isDeliveryPicked: Boolean) {
        val totalPrice =
            if (isDeliveryPicked) subtotalPrice + restaurant.deliveryCharge else subtotalPrice
        binding.tvTotal.text = getString(R.string.title_price_unit, totalPrice)
    }

    private fun checkForm() {
        val name = binding.edtName.text.toString()
        val isDeliveryPicked = binding.switchDelivery.isChecked
        val address = binding.edtAddress.text.toString()
        val city = binding.edtCity.text.toString()
        val state = binding.edtState.text.toString()
        val zipCode = binding.edtZip.text.toString()
        val cardNumber = binding.edtCardNumber.text.toString()
        val cardExpiry = binding.edtCardExpiry.text.toString()
        val cardPin = binding.edtCardPin.text.toString()

        when {
            name.isBlank() -> showError(binding.edtNameLayout, R.string.hint_name)

            isDeliveryPicked && address.isBlank() -> showError(
                binding.edtAddressLayout,
                R.string.hint_address
            )

            isDeliveryPicked && city.isBlank() -> showError(
                binding.edtCityLayout,
                R.string.hint_city
            )

            isDeliveryPicked && state.isBlank() -> showError(
                binding.edtStateLayout,
                R.string.hint_state
            )

            isDeliveryPicked && zipCode.isBlank() -> showError(
                binding.edtZipLayout,
                R.string.hint_zip_code
            )

            cardNumber.isBlank() -> showError(
                binding.edtCardNumberLayout,
                R.string.hint_card_number
            )

            cardExpiry.isBlank() -> showError(
                binding.edtCardExpiryLayout,
                R.string.hint_card_expiry
            )

            cardPin.isBlank() -> showError(binding.edtCardPinLayout, R.string.hint_card_pin)

            else -> orderNow()
        }
    }

    private fun showError(textInputLayout: TextInputLayout, @StringRes hintName: Int) {
        textInputLayout.error =
            getString(R.string.err_empty, getString(hintName))
        binding.scrollView.smoothScrollTo(0, 0)
    }

    private fun orderNow() {
        // order items to your server here

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.cartItemDao().deleteAllFromRestaurant(restaurant.name)
            launchOrderSuccessActivity()
        }
    }

    private fun launchOrderSuccessActivity() {
        startActivity(Intent(this, OrderSuccessActivity::class.java))
    }
}