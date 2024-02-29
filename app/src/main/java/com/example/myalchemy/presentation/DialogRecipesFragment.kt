package com.example.myalchemy.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.myalchemy.R
import com.example.myalchemy.databinding.FragmentDialogRecipesBinding
import com.example.myalchemy.domain.entity.Element

class DialogRecipesFragment : DialogFragment() {

    lateinit var binding: FragmentDialogRecipesBinding
    private lateinit var parents: List<Element>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.setImageResource(getImage())
        binding.textView.text = getName()
        getParent(binding.lList, view)
    }

    private fun getParent(linearLayout: LinearLayout, view: View){
        requireArguments().getParcelableArrayList<Element>(PARENT)?.let{
            parents = it.toList()
        }
        for (parent in parents){
            val inflatedView = LayoutInflater.from(view.context).inflate(R.layout.element_item, linearLayout, false)

            val imageView: ImageView = inflatedView.findViewById(R.id.image)

            imageView.setImageResource(parent.image)

            linearLayout.addView(inflatedView)
        }
    }

    private fun getImage() : Int{
        return requireArguments().getInt(IMAGE)
    }

    private fun getName(): String? {
        return requireArguments().getString(NAME_ELEMENTS)
    }

    companion object {
        const val NAME = "Recipes"

        const val NAME_ELEMENTS = "Name"
        const val IMAGE = "Image"
        const val PARENT = "Parent"

        fun newInstance(name: String, image: Int, parent: List<Element>) : DialogRecipesFragment{
            val arguments = Bundle().apply {
                putString(NAME_ELEMENTS, name)
                putInt(IMAGE, image)
                putParcelableArrayList(PARENT, ArrayList(parent))
            }
            val fragment = DialogRecipesFragment()
            fragment.arguments = arguments
            return fragment
        }


    }
}