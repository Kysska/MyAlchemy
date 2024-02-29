package com.example.myalchemy.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myalchemy.R
import com.example.myalchemy.data.room.ElementDBEntity
import com.example.myalchemy.data.room.ElementDao
import com.example.myalchemy.domain.GameRepository
import com.example.myalchemy.domain.entity.Element
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameRepositoryImpl(private val elementDao: ElementDao) : GameRepository {
    private val elementsLD = MutableLiveData<Map<Int, Element>>()
    private val elements = mutableMapOf<Int, Element>()

    init {
        elements.putAll(
            mapOf(
                1 to Element(
                    id = 1,
                    name = "Земля",
                    image = R.drawable.soil,
                    parent = emptyList(),
                    open = true
                ),
                2 to Element(
                    id = 2,
                    name = "Вода",
                    image = R.drawable.water_drop,
                    parent = emptyList(),
                    open = true
                ),
                3 to Element(
                    id = 3,
                    name = "Огонь",
                    image = R.drawable.fire,
                    parent = emptyList(),
                    open = true
                ),
                4 to Element(
                    id = 4,
                    name = "Воздух",
                    image = R.drawable.wind,
                    parent = emptyList(),
                    open = true
                ),
                12 to Element(
                    id = 12,
                    name = "Растение",
                    image = R.drawable.plant,
                    parent = listOf("1", "2"),
                    open = false
                ),
                13 to Element(
                    id = 13,
                    name = "Лава",
                    image = R.drawable.volcano,
                    parent = listOf("1", "3"),
                    open = false
                ),
                23 to Element(
                    id = 23,
                    name = "Пар",
                    image = R.drawable.hot_water,
                    parent = listOf("2", "3"),
                    open = false
                ),
                24 to Element(
                    id = 24,
                    name = "Облако",
                    image = R.drawable.clouds,
                    parent = listOf("2", "4"),
                    open = false
                ),
                34 to Element(
                    id = 34,
                    name = "Жара",
                    image = R.drawable.overheat,
                    parent = listOf("3", "4"),
                    open = false
                ),
                112 to Element(
                    id = 112,
                    name = "Трава",
                    image = R.drawable.grass,
                    parent = listOf("1", "12"),
                    open = false
                ),


                )
        )
        CoroutineScope(Dispatchers.IO).launch {
            filterWithDatabase()
        }
    }


    private fun updateLiveData() {
        elementsLD.postValue(elements.toMap())
    }

    override fun getAllElements(): LiveData<Map<Int, Element>> {
        return elementsLD
    }

    override fun getParents(curElement: Int): List<Element> {
        val parentString = elements[curElement]?.parent ?: emptyList()
        val parentElement = mutableListOf<Element>()
        for (parent in parentString) {
            elements[parent.toInt()]?.let { parentElement.add(it) }
        }
        return parentElement
    }

    override fun openElements(parents: List<String>): Boolean {
        val combination: String = parents.reduce { acc, num -> acc + num }
        val intCombination: Int = combination.toInt()
        if (elements.contains(intCombination)) {
            val thisElements = elements[intCombination]
            if (thisElements != null && !thisElements.open) {
                thisElements.open = true
                elements[intCombination] = thisElements
            }
            else{
                return false
            }
            updateLiveData()
            CoroutineScope(Dispatchers.IO).launch {
                insert(intCombination)
            }
            return true
        } else {
            return false
        }
    }

    private suspend fun insert(keyId : Int) {
        elementDao.insert(ElementDBEntity(keyId = keyId))
    }

    private fun filterWithDatabase(){
        val listKeyElements :List<Int> = elementDao.getAllKey()  
        for(key in listKeyElements){
            elements[key]?.open = true
        }
        updateLiveData()
    }


}