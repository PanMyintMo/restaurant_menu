package com.example.foodsample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsample.R
import com.example.foodsample.databinding.MenuRowBinding
import com.example.foodsample.models.Menu
import java.util.zip.Inflater

class MenuListAdapter(val menuList: List<Menu?>?, val clickListener: MenuListClickListener) :
    RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.MyViewHolder, position: Int) {
        menuList!!.get(position)?.let { holder.bind(holder.binding,it) }
    }

    override fun getItemCount(): Int {
        return if (menuList == null) return 0 else menuList.size
    }

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = MenuRowBinding.bind(view)
        fun bind(binding: MenuRowBinding,menus: Menu) {
            binding.menuName.text = menus.name
            binding.menuPrice.text = "Price: $ ${menus.price}"

            binding.addToCartButton.setOnClickListener {
                menus.totalInCart = 1
                clickListener.addToCartClickListener(menus)
                binding.addMoreLayout.visibility = View.VISIBLE
                binding.addToCartButton.visibility = View.GONE
                binding.tvCount.text = menus.totalInCart.toString()
            }
            binding.imageMinus.setOnClickListener {
                var total: Int = menus.totalInCart!!
                total--
                if (total > 0) {
                    menus.totalInCart = total
                    clickListener.updateCartClickListener(false, menus)
                    binding.tvCount.text = menus.totalInCart.toString()
                } else {
                    menus.totalInCart = total
                    clickListener.removeFromCartClickListener(menus)
                    binding.addMoreLayout.visibility = View.GONE
                    binding.addToCartButton.visibility = View.VISIBLE
                }
            }
            binding.imageAddOne.setOnClickListener {
                var total: Int = menus.totalInCart!!
                total++
                if (total <= 10) {
                    menus.totalInCart = total
                    clickListener.updateCartClickListener(true, menus)
                    binding.tvCount.text = total.toString()
                }
            }

            Glide.with(binding.thumbImage)
                .load(menus.url)
                .into(binding.thumbImage)
        }
    }

    interface MenuListClickListener {

        fun addToCartClickListener(menu: Menu)
        fun updateCartClickListener(isAdding: Boolean, menu: Menu)
        fun removeFromCartClickListener(menu: Menu)
    }
}


