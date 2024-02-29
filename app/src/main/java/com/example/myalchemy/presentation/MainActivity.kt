package com.example.myalchemy.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myalchemy.R
import com.example.myalchemy.databinding.ActivityMainBinding
import com.example.myalchemy.domain.entity.Element

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = ElementListAdapter()
    private val adapterClose = ElementCloseListAdapter()
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initBottomSheet()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)
        viewModel.filteredOpenElementsListLD.observe(this) { filteredOpenElements ->
            adapter.list = filteredOpenElements.values.toList()
        }

        viewModel.filteredCloseElementsListLD.observe(this) { filteredCloseElements ->
            adapterClose.list = filteredCloseElements.values.toList()
        }
    }

    private fun init(){
        binding.apply {
            rvElementOpen.layoutManager = GridLayoutManager(this@MainActivity, 4)
            rvElementOpen.adapter = adapter

            rvElementClose.layoutManager = GridLayoutManager(this@MainActivity, 4)
            rvElementClose.adapter = adapterClose

            adapter.onElementLongClickListener = object : ElementListAdapter.OnElementListLongClickListener{
                override fun onElementLongClickListener(element: Element) {
                    val fragment = DialogRecipesFragment.newInstance(
                        element.name, element.image, viewModel.onGetParents(element.id)
                    )
                    fragment.show(supportFragmentManager, DialogRecipesFragment.NAME)

                }

                override fun onElementClickListener(element: Element) {
                   viewModel.setElementImageList(element.image, element.id)
                }

            }
        }
    }

    private fun initBottomSheet(){
        val bottomSheetFragment = ShakeElementsFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_shake, bottomSheetFragment).commit()
    }
}