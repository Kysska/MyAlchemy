package com.example.myalchemy.domain

import androidx.lifecycle.LiveData
import com.example.myalchemy.domain.entity.Element

interface GameRepository {
    fun getAllElements() : LiveData<Map<Int, Element>>

    fun getParents(curElement : Int) : List<Element>

    fun openElements(parents : List<String>) : Boolean

}