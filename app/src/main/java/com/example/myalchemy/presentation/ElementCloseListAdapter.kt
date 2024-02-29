package com.example.myalchemy.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myalchemy.R
import com.example.myalchemy.databinding.ElementCloseItemBinding
import com.example.myalchemy.domain.entity.Element


class ElementCloseListAdapter : RecyclerView.Adapter<ElementCloseListAdapter.ElementCloseHolder>() {

    var list = listOf<Element>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    class ElementCloseHolder(view: View) : RecyclerView.ViewHolder(view){
         val binding = ElementCloseItemBinding.bind(view)
        fun bind(element: Element){
            binding.image.setImageResource(element.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementCloseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_close_item, parent, false)
        return ElementCloseHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ElementCloseHolder, position: Int) {
        holder.bind(list[position])
    }
}