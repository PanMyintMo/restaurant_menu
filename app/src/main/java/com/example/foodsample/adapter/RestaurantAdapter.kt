package com.example.foodsample.adapter

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodsample.R
import com.example.foodsample.databinding.ItemRestaurantBinding
import com.example.foodsample.models.Hours
import com.example.foodsample.models.Restaurant
import com.example.foodsample.ui.MainActivity
import com.example.foodsample.ui.RestaurantMenuActivity
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAdapter(
    private val restaurants: ArrayList<Restaurant>,
    private val onItemClickedListener: OnItemClickedListener
) :
    RecyclerView.Adapter<RestaurantAdapter.PlaceHolder>() {

    private val calendar = java.util.Calendar.getInstance()
    private val todayName =
        calendar.getDisplayName(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.LONG, Locale.ENGLISH) ?: "Sunday"

    inner class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRestaurantBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        return PlaceHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.binding.apply {
            tvName.text = restaurant.name
            tvAddress.text = restaurant.address
            tvOpenHours.text = getTodayOpenHours(restaurant.hours)

            Glide.with(ivThumbnail)
                .load(restaurant.image)
                .placeholder(R.drawable.ic_restaurant_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivThumbnail)
            root.setOnClickListener {

                onItemClickedListener.onItemClick(restaurant)
                val intent = Intent(holder.itemView.context, RestaurantMenuActivity::class.java)
            intent.putExtra(RestaurantMenuActivity.EXTRA_RESTAURANT, restaurant)
            holder.itemView.context.startActivity(intent)
        }
        }
    }

    private fun getTodayOpenHours(hours: Hours): String {
        return when (todayName) {
            "Sunday" -> hours.sunday
            "Monday" -> hours.monday
            "Tuesday" -> hours.tuesday
            "Wednesday" -> hours.wednesday
            "Thursday" -> hours.thursday
            "Friday" -> hours.friday
            "Saturday" -> hours.saturday
            else -> hours.sunday
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    interface OnItemClickedListener {
        fun onItemClick(restaurant: Restaurant)
    }
}