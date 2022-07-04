package com.example.foodsample.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsample.R
import com.example.foodsample.databinding.MenuRowBinding
import com.example.foodsample.models.Menu
import com.google.android.material.badge.BadgeDrawable

class MenuListAdapter(val menuList: MutableList<Menu>, val clickListener: MenuListClickListener) :
    RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {

    private var currentSelection = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuListAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.MyViewHolder, position: Int) {
        holder.bind(holder.binding, menuList[position],selected = true)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val binding = MenuRowBinding.bind(view)
        fun bind(binding: MenuRowBinding, menu: Menu,selected: Boolean) {

            binding.menuName.text = menu.name
            binding.menuPrice.text = "Price: $ ${menu.price}"
           binding.addToCartButton.isSelected = selected
            binding.addToCartButton.setOnClickListener {
                clickListener.addToCartClickListener(menu,selected)
               binding.addToCartButton.isSelected = !binding.addToCartButton.isSelected
            }
            Glide.with(binding.thumbImage)
                .load(menu.url)
                .into(binding.thumbImage)
        }

    }

    interface MenuListClickListener {
        fun addToCartClickListener(menu: Menu,selected:Boolean)

    }

}

