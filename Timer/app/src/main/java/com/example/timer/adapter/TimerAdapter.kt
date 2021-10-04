package com.example.timer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.database.Timer
import com.example.timer.fragment.TimerListFragmentDirections

class TimerAdapter : RecyclerView.Adapter<TimerAdapter.ItemViewHolder>()
{
    private var timerList = emptyList<Timer>()

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        //val button: Button = view.findViewById(R.id.button228)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = timerList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = timerList[position]
//        holder.button.text = "345"
//
//        holder.button.setOnClickListener {
//            val action = TimerListFragmentDirections.actionTimerListFragmentToTimerFragment(timer = "123")
//            holder.view.findNavController().navigate(action)
//        }
    }

    fun setData(timerList: List<Timer>){
        this.timerList = timerList
        notifyDataSetChanged()
    }
}