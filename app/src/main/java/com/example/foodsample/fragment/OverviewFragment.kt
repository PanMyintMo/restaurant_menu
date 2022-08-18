package com.example.foodsample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodsample.databinding.FragmentOverviewBinding
import com.example.foodsample.models.RestaurantMenu



class OverviewFragment : Fragment() {

    private lateinit var restaurantMenu: RestaurantMenu
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val rest = arguments?.getParcelable<RestaurantMenu>("list").let {
            restaurantMenu = it!!
        }

       rest.apply {
           binding.summarTextView.text=restaurantMenu.overview
       }

        val url = rest.let {
            restaurantMenu.url
        }

        Glide.with(binding.mainImageView)
            .load(url)
            .into(binding.mainImageView)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}