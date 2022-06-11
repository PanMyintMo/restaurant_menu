package com.example.foodsample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsample.databinding.PlaceYourOrderRowBinding

class PlaceYourOrderAdapter(private val menuList: List<com.example.foodsample.models.Menu?>?) :
    RecyclerView.Adapter<PlaceYourOrderAdapter.CustomViewHolder>() {

    private lateinit var binding: PlaceYourOrderRowBinding

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(menu: com.example.foodsample.models.Menu) {
            binding.menuName.text = menu.name
            binding.menuPrice.text = "Price $ " + String.format(
                "%.2f",
                menu.price?.times(menu.totalInCart!!) ?: (menu.price!! * menu.totalInCart!!)
            )
            binding.menuQty.text = "Qty :" + menu.totalInCart

            Glide.with(binding.thumbImage)
                .load(menu.url)
                .into(binding.thumbImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = PlaceYourOrderRowBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        menuList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return menuList?.size!!
    }
}