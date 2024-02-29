package com.example.myalchemy.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myalchemy.data.GameRepositoryImpl
import com.example.myalchemy.data.room.AppDatabase
import com.example.myalchemy.data.room.ElementDao
import com.example.myalchemy.domain.GameRepository
import com.example.myalchemy.domain.entity.Element
import com.example.myalchemy.domain.usecase.GetAllElements
import com.example.myalchemy.domain.usecase.GetParents
import com.example.myalchemy.domain.usecase.OpenElements

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : GameRepositoryImpl

    init {
        val dao = AppDatabase.getDatabase(application).getElementDao()
        repository = GameRepositoryImpl(dao)
    }
    private val getAllElements = GetAllElements(repository)

    private val getParents = GetParents(repository)
    private val openElements = OpenElements(repository)

    private var elementsListLD = getAllElements.getAllElements()

    fun onGetParents(curElement: Int) : List<Element>{
       return getParents.getParents(curElement)
    }
    fun openElement(parent: List<String>){
        if(openElements.openElements(parent)){
            updateAllElements()
        }
    }

    val filteredOpenElementsListLD: LiveData<Map<Int, Element>> =
        Transformations.map(elementsListLD) { elementsMap ->
            elementsMap?.filter { (_, value) -> value.open } ?: emptyMap()
        }

    val filteredCloseElementsListLD: LiveData<Map<Int, Element>> =
        Transformations.map(elementsListLD) { elementsMap ->
            elementsMap?.filter { (_, value) -> !value.open } ?: emptyMap()
        }

    var imageLD = MutableLiveData<List<Int>>()
    var imageL = mutableListOf<Int>()

    var idLD = MutableLiveData<List<Int>>()
    var idL = mutableListOf<Int>()

    fun setElementImageList(newImage: Int, newId: Int) {
        val indexOfZero = imageL.indexOf(0)
        if (indexOfZero != -1) {
            idL[indexOfZero] = newId
            imageL[indexOfZero] = newImage
            idLD.value = idL
            imageLD.value = imageL
        } else if (idL.size < MAX_ELEMENTS) {
            idL.add(newId)
            imageL.add(newImage)
            idLD.value = idL
            imageLD.value = imageL
        }
    }

    fun removeElement(i: Int){
        idL[i] = 0
        imageL[i] = 0
        idLD.value = idL
        imageLD.value = imageL
    }

    private fun updateAllElements(){
        elementsListLD = getAllElements.getAllElements()
    }

    companion object{
        private val MAX_ELEMENTS = 3
    }

}
