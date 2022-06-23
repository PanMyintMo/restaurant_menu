package com.example.foodsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsample.R
import com.example.foodsample.databinding.MenuRowBinding
import com.example.foodsample.models.Menu

class MenuListAdapter(private val menuList: List<Menu>, val clickListener: MenuListClickListener) :
    RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuListAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.MyViewHolder, position: Int) {
        holder.bind(holder.binding, menuList[position])
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val binding = MenuRowBinding.bind(view)
        fun bind(binding: MenuRowBinding, menu: Menu) {
            binding.menuName.text = menu.name
            binding.menuPrice.text = "Price: $ ${menu.price}"

            binding.addToCartButton.setOnClickListener {
                menu.totalInCart = 1
                binding.addMoreLayout.visibility = View.VISIBLE
                binding.addToCartButton.visibility = View.GONE
                binding.tvCount.text = menu.totalInCart.toString()
                clickListener.addToCartClickListener(menu)
            }

            binding.imageMinus.setOnClickListener {
                menu.totalInCart--
                if (menu.totalInCart > 0) {
                    binding.tvCount.text = menu.totalInCart.toString()
                } else {
                    binding.addMoreLayout.visibility = View.GONE
                    binding.addToCartButton.visibility = View.VISIBLE
                }

                clickListener.removeFromCartClickListener(menu)
            }

            binding.imageAddOne.setOnClickListener {
                if (menu.totalInCart!! < 10) {
                    menu.totalInCart++
                    binding.tvCount.text = menu.totalInCart.toString()
                    clickListener.addToCartClickListener(menu)
                }
            }

            Glide.with(binding.thumbImage)
                .load(menu.url)
                .into(binding.thumbImage)
        }
    }

    interface MenuListClickListener {
        fun addToCartClickListener(menu: Menu)
        fun removeFromCartClickListener(menu: Menu)
    }
}


