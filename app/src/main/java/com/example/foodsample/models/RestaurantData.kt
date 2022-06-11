package com.example.foodsample.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantDataModel(val name:String?, val address: String?,
                               val delivery_charge:String?, val image:String?, val hours:Hours?,
                               var menus:List<Menu?>?) :
    Parcelable

@Parcelize
data class Hours(val Monday:String?, val Tuesday:String?, val Wednesday:String?,
                   val Thursday:String?, val Friday :String?, val Saturday:String?, val Sunday:String?) :
    Parcelable

@Parcelize
data class Menu(val name:String?, val price :Float?, val url:String?, var totalInCart:Int?) :
    Parcelable