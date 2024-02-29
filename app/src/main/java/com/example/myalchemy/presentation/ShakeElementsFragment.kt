package com.example.myalchemy.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myalchemy.R
import com.example.myalchemy.databinding.FragmentShakeElementsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShakeElementsFragment : Fragment() {

    lateinit var binding: FragmentShakeElementsBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var idList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShakeElementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.imageLD.observe(viewLifecycleOwner){
            if (it != null) {
                for(i in it.indices){
                    when(i){
                        0-> binding.imageView.setImageResource(it[i])
                        1->binding.imageView2.setImageResource(it[i])
                        2->binding.imageView3.setImageResource(it[i])
                    }

                    }
                }
            }
        viewModel.idLD.observe(viewLifecycleOwner){
            idList = it
            Log.d("idList", it.size.toString())
        }

        binding.imageView.setOnClickListener {
            viewModel.removeElement(0)
        }
        binding.imageView2.setOnClickListener {
            viewModel.removeElement(1)
        }
        binding.imageView3.setOnClickListener {
            viewModel.removeElement(2)
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.openElement(idList.filter { it != 0 }.sorted().map { it.toString() })

        }
    }


    companion object {
        const val NAME = "ShakeElement"

        const val IMAGE = "Image"
        const val ID = "Id"

        fun newInstance() : ShakeElementsFragment{

            return ShakeElementsFragment()

        }

    }
}