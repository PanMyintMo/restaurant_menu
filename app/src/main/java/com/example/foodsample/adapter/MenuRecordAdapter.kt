package com.example.foodsample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodsample.R
import com.example.foodsample.adapter.MenuRecordAdapter.CustomViewHolder
import com.example.foodsample.databinding.MenuRecordRowBinding
import com.example.foodsample.models.Menu


class MenuRecordAdapter(private val menuRecordList: List<Menu?>?,val clickListener :OnItemClick) :
    RecyclerView.Adapter<CustomViewHolder>() {

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val binding = MenuRecordRowBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(binding: MenuRecordRowBinding, menu: Menu,position: Int) {
            binding.cartName.text = menu.name
            binding.cartPrice.text = "Price :" + menu.price.toString()
            binding.cartQuality.text = "Qty :" + menu.totalInCart

            binding.button.setOnClickListener {

                clickListener.deleteCartListItem(menu,position)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_record_row, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        menuRecordList?.get(position).let {
            it?.let { it1 -> holder.bind(holder.binding, it1,position) }
        }
    }

    override fun getItemCount(): Int {
        return menuRecordList!!.size
    }


    interface OnItemClick{
        fun deleteCartListItem(menu: Menu, position: Int)
    }

}

