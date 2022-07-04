package com.example.foodsample.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsample.R
import com.example.foodsample.adapter.MenuRecordAdapter.CustomViewHolder
import com.example.foodsample.databinding.MenuRecordRowBinding
import com.example.foodsample.models.Menu
import database.MenuDatabase


class MenuRecordAdapter(
    val context: Context,
    private val menuRecordList: MutableList<Menu?>?
) :
    RecyclerView.Adapter<CustomViewHolder>() {
    private val menuDatabase by lazy {
        MenuDatabase.getDatabase(context).DaoMenu()
    }


    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MenuRecordRowBinding.bind(view)
        var count = 0

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bind(binding: MenuRecordRowBinding, menu: Menu, position: Int) {

            menu.totalInCart = 1
            binding.cartName.text = menu.name
            binding.cartPrice.text =
                "Price: $" + String.format("%.2f", menu.price.times(menu.totalInCart))
            binding.imageAddOne.setOnClickListener {
                menu.totalInCart++
                binding.cartPrice.text =
                    "Price: $" + String.format("%.2f", menu.price.times(menu.totalInCart))
                binding.cartQuality.text = "Qty :" + menu.totalInCart
                binding.tvCount.text = menu.totalInCart.toString()
            }

            binding.imageMinus.setOnClickListener {
                menu.totalInCart--
                binding.cartPrice.text =
                    "Price: $" + String.format("%.2f", menu.price.times(menu.totalInCart))
                binding.cartQuality.text = "Qty :" + menu.totalInCart

                if (menu.totalInCart <= 0) {
                    menuRecordList?.removeAt(position)
                    count++
                    notifyItemRemoved(position)
                    menuRecordList.let { it1 ->
                        if (it1 != null) {
                            notifyItemRangeChanged(position, it1.size)
                        }
                        notifyDataSetChanged()
                    }
                    binding.tvCount.text = menu.totalInCart.toString()
                    if (menuRecordList != null) {
                        removeFromDB(menuRecordList)
                    }
                }
                Glide.with(binding.cartImage).load(menu.url).into(binding.cartImage)
            }
        }
    }

    fun removeFromDB(remainingItem: MutableList<Menu?>) {

    //    menuDatabase.deleteMenuItem(remainingItem as ArrayList<Menu?>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_record_row, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        menuRecordList?.get(position).let {
            it.let { it1 -> it1?.let { it2 -> holder.bind(holder.binding, it2, position) } }
        }
    }

    override fun getItemCount(): Int {
        return menuRecordList!!.size
    }
}

