package com.example.converter.model

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.view.MainActivity

class RecyclerAdapter(private val listener : (CBRates) -> Unit) : ListAdapter<CBRates, RecyclerAdapter.ViewHolder>(DiffUserCallBack()){

    inner class ViewHolder(private val containerView : View) : RecyclerView.ViewHolder(containerView){
        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition))
            }
        }

        fun bind(user : CBRates){
            containerView.findViewById<TextView>(R.id.name).text = user.name
            containerView.findViewById<TextView>(R.id.charCode).text = user.charCode
            containerView.findViewById<TextView>(R.id.value).text = "Сейчас - " + user.value
            containerView.findViewById<TextView>(R.id.prev).text = "Вчера - " + user.pvalue
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffUserCallBack : DiffUtil.ItemCallback<CBRates>(){
    override fun areItemsTheSame(oldItem: CBRates, newItem: CBRates): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CBRates, newItem: CBRates): Boolean {
        TODO("Not yet implemented")
    }

}