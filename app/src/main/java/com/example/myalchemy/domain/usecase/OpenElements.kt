package com.example.myalchemy.domain.usecase

import com.example.myalchemy.data.GameRepositoryImpl

class OpenElements(private val repository: GameRepositoryImpl) {
    fun openElements(parents : List<String>) : Boolean{
        return repository.openElements(parents)
    }
}