package com.example.foodsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodsample.databinding.MenuRowBinding
import com.example.foodsample.models.Reviews

class ReviewsAdapter(private val reviewList: List<Reviews?>?) :
    RecyclerView.Adapter<ReviewsAdapter.CustomReviews>() {

    private lateinit var binding: MenuRowBinding

    inner class CustomReviews(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(reviews: Reviews?) {

            binding.revName.text =reviews?.name
            binding.revDate.text = reviews?.date
            binding.revRating.text=reviews?.rating.toString()
            binding.revComment.text = reviews?.comments

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomReviews {

        val inflater = LayoutInflater.from(parent.context)

        binding = MenuRowBinding.inflate(inflater, parent, false)
        return CustomReviews(binding.root)

    }

    override fun onBindViewHolder(holder: CustomReviews, position: Int) {
        holder.bind(reviewList?.get(position)!!)

    }

    override fun getItemCount(): Int {
        return reviewList?.size!!
    }
}