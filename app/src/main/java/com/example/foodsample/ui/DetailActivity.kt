package com.example.foodsample.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.foodsample.R
import com.example.foodsample.adapter.PagerAdapter
import com.example.foodsample.databinding.ActivityDetailBinding
import com.example.foodsample.fragment.IngredientFragment
import com.example.foodsample.fragment.InstructionFragment
import com.example.foodsample.fragment.OverviewFragment
import com.example.foodsample.models.RestaurantMenu
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : BaseActivity() {
    private lateinit var restaurantMenu: RestaurantMenu
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isTaskRoot)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        restaurantMenu= intent.getParcelableExtra("restaurant")!!

        val fragment = ArrayList<Fragment>()
        fragment.add(OverviewFragment())
        fragment.add(InstructionFragment())
        val ingredientFragment= IngredientFragment()

        val bundle=Bundle()

        bundle.putParcelable("list",restaurantMenu)
        ingredientFragment.arguments=bundle


        fragment.add(IngredientFragment())
        val title = ArrayList<String>()
        title.add("Overview")
        title.add("Instruction")
        title.add("Ingredient")



        val pagerAdapter = PagerAdapter(bundle,fragment, this)

        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = title[position]
        }
            .attach()
    }
}