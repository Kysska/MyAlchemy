package com.example.myalchemy.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Element (
  val id : Int,
  val name: String,
  val image: Int,
  val parent: List<String>,
  var open: Boolean
        ) : Parcelable{

}