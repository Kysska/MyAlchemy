package com.example.myalchemy.domain.usecase

import androidx.lifecycle.LiveData
import com.example.myalchemy.data.GameRepositoryImpl
import com.example.myalchemy.domain.entity.Element

class GetAllElements(private val repository: GameRepositoryImpl) {
    fun getAllElements() : LiveData<Map<Int, Element>>{
        return repository.getAllElements()
    }
}