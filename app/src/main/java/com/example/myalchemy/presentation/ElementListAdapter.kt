package com.example.myalchemy.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myalchemy.R
import com.example.myalchemy.databinding.ElementItemBinding
import com.example.myalchemy.domain.entity.Element

class ElementListAdapter: RecyclerView.Adapter<ElementListAdapter.ElementHolder>() {


    var list = listOf<Element>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var onElementLongClickListener: OnElementListLongClickListener? = null

    class ElementHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val binding = ElementItemBinding.bind(view)
        fun bind(element: Element){
            binding.image.setImageResource(element.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.element_item, parent, false)
        return ElementHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ElementHolder, position: Int) {
        holder.bind(list[position])
        val element = list[position]
        holder.view.setOnLongClickListener{
            onElementLongClickListener?.onElementLongClickListener(element)
            true
        }
        holder.view.setOnClickListener {
            onElementLongClickListener?.onElementClickListener(element)
            true
        }
    }

    interface OnElementListLongClickListener{
        fun onElementLongClickListener(element: Element)
        fun onElementClickListener(element: Element)
    }


}