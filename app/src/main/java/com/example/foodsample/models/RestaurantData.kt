package com.example.foodsample.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantDataModel(val id:Int?,val name:String?,val neighborhood:String?,
                          val photograph:String?, val address:String?, val operating_hours: Hours?, val reviews: List<Reviews?>?) :
    Parcelable

@Parcelize
data class Hours(val Monday:String?, val Tuesday:String?, val Wednesday:String?,
                   val Thursday:String?, val Friday :String?, val Saturday:String?, val Sunday:String?) :
    Parcelable

@Parcelize
data class Reviews(val name:String?, val date :String?, val rating:Int?, val comments:String?) :
    Parcelable