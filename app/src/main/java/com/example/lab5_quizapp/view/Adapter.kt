package com.example.lab5_quizapp.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5_quizapp.R

class Adapter (private val list: ArrayList<String>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private lateinit var clickListener: onItemClickListener
    private lateinit var context: Context

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnClick(listener: onItemClickListener){
        clickListener = listener
    }

    inner class ViewHolder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val item :TextView = itemView.findViewById(R.id.item)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test,parent,false)
        return ViewHolder(view,clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = list[position]
        holder.item.text = itemView

        if(position % 1 == 0){
            holder.item.setBackgroundColor(Color.parseColor("#2ECC71"))
        }
        if(position % 2 == 0){
            holder.item.setBackgroundColor(Color.parseColor("#F39C12"))
        }
        if(position % 3 == 0){
            holder.item.setBackgroundColor(Color.parseColor("#5D6D7E"))
        }
        if(position % 4 == 0){
            holder.item.setBackgroundColor(Color.parseColor("#E6B0AA"))
        }
        if(position % 5 == 0){
            holder.item.setBackgroundColor(Color.parseColor("#5499C7"))
        }
        if(position % 6 == 0){
            holder.item.setBackgroundColor(Color.parseColor("#B03A2E"))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}