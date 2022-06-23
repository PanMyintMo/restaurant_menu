package com.example.foodsample.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class RestaurantDataModel(
    val name: String?, val address: String?,val price: Float,
    val delivery_charge: String?, val image: String?, val hours: Hours?,
    var menus: List<Menu?>?
) :
    Parcelable

@Parcelize
data class Hours(
    val Monday: String?, val Tuesday: String?, val Wednesday: String?,
    val Thursday: String?, val Friday: String?, val Saturday: String?, val Sunday: String?
) :
    Parcelable
@Parcelize
data class Menu(
    val name: String?, val price: Float, val url: String?, var totalInCart: Int, val time: String?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return other != null && other is Menu && other.name == this.name
    }

    override fun hashCode(): Int {
        return name?.hashCode() ?: 0
    }
}