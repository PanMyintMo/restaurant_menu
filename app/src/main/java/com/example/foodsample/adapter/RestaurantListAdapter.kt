package com.example.foodsample.adapter

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsample.R
import com.example.foodsample.databinding.RestaurantDataRowBinding
import com.example.foodsample.models.Hours
import com.example.foodsample.models.RestaurantDataModel
import java.text.SimpleDateFormat
import java.util.*

class RestaurantListAdapter(
    private val restaurantList: List<RestaurantDataModel>?,
    val clickListener: RestaurantClickListener
) : RecyclerView.Adapter<RestaurantListAdapter.CustomRestaurantHolder>() {

    private lateinit var binding: RestaurantDataRowBinding

    inner class CustomRestaurantHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(restaurentModel: RestaurantDataModel?) {
            binding.tvRestaurantName.text = restaurentModel?.name
            binding.tvRestaurantAddress.text = "Address: " + restaurentModel?.address
            binding.tvRestaurantHours.text =
                "Today's Hours: " + getTodayHour(restaurentModel?.hours!!)

            Glide.with(binding.thumbImage)
                .load(restaurentModel.image)
                .into(binding.thumbImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRestaurantHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RestaurantDataRowBinding.inflate(inflater, parent, false)
        return CustomRestaurantHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CustomRestaurantHolder, position: Int) {
        holder.bind(restaurantList?.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(restaurantList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList?.size!!
    }

    fun getTodayHour(hours: Hours): String? {

        val calendar: Calendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val date: Date = calendar.time
        val day: String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)

        return when (day) {
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday
        }
    }


    interface RestaurantClickListener {

        fun onItemClick(restaurantData: RestaurantDataModel?)

    }

}

