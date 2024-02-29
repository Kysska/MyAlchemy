package com.example.myalchemy.domain.usecase

import com.example.myalchemy.data.GameRepositoryImpl
import com.example.myalchemy.domain.entity.Element

class GetParents(private val repository: GameRepositoryImpl) {

    fun getParents(curElement : Int) : List<Element>{
        return repository.getParents(curElement)
    }
}