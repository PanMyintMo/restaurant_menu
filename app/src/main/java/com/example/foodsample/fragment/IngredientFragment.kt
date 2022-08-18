package com.example.foodsample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodsample.adapter.IngredientAdapter
import com.example.foodsample.databinding.FragmentIngredientBinding
import com.example.foodsample.models.Ingredient
import com.example.foodsample.models.RestaurantMenu
import kotlin.collections.ArrayList

class IngredientFragment : Fragment() {
    private lateinit var adapter: IngredientAdapter
    private lateinit var restaurantMenu: RestaurantMenu
    private lateinit var imageItems:String
    private var ingredientList: ArrayList<Ingredient> = arrayListOf()
    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIngredientBinding.inflate(inflater, container, false)

        val arguments = arguments
        arguments?.getParcelable<RestaurantMenu>("list")
            .let {
                restaurantMenu = it!!
            }

        arguments?.let {
            ingredientList = restaurantMenu.ingredients

            for (item in ingredientList) {
                imageItems= item.img.toString()
            }

            setUpRecyclerView(ingredientList, imageItems)
        }
        return binding.root
    }

    private fun setUpRecyclerView(
        ingredient: ArrayList<Ingredient>,
        url: String
    ) {
        binding.ingredientRecycler.setHasFixedSize(true)
        binding.ingredientRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = IngredientAdapter(ingredient, url)
        binding.ingredientRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}