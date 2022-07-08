package com.example.foodsample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodsample.R
import com.example.foodsample.databinding.ItemRestaurantMenuBinding
import com.example.foodsample.models.RestaurantMenu
class RestaurantMenuAdapter(
    private val context: Context,
    private val restaurantMenuList: ArrayList<RestaurantMenu>,
    private val cartCheckedList: ArrayList<Boolean>,
    private val onCartClickedListener: OnCartClickedListener
) :
    RecyclerView.Adapter<RestaurantMenuAdapter.PlaceHolder>() {

    inner class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRestaurantMenuBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        return PlaceHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_restaurant_menu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val restaurantMenu = restaurantMenuList[position]
        var isCartChecked = cartCheckedList[position]

        holder.binding.apply {
            tvName.text = restaurantMenu.name
            tvPrice.text =
                context.getString(R.string.title_price_unit, restaurantMenu.price)
            btnCart.setIconResource(getCartIcon(isCartChecked))

            Glide.with(ivThumbnail)
                .load(restaurantMenu.url)
                .placeholder(R.drawable.ic_restaurant_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivThumbnail)

            btnCart.setOnClickListener {
                isCartChecked = !isCartChecked
                btnCart.setIconResource(getCartIcon(isCartChecked))
                onCartClickedListener.onCartClick(
                    holder.adapterPosition,
                    isCartChecked
                )
            }
        }
    }

    @DrawableRes
    private fun getCartIcon(isCartChecked: Boolean): Int {
        return if (isCartChecked) R.drawable.ic_remove_shopping_cart else R.drawable.ic_add_shopping_cart
    }

    override fun getItemCount(): Int {
        return restaurantMenuList.size
    }

    interface OnCartClickedListener {
        fun onCartClick(position: Int, isCartChecked: Boolean)
    }
}