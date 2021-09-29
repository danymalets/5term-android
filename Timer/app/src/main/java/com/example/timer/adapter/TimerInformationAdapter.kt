package com.example.timer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.TimerListFragment
import com.example.timer.TimerListFragmentDirections
import com.example.timer.model.TimerInformation

class TimerInformationAdapter(
    private val context: TimerListFragment,
    private val dataset: List<TimerInformation>
) : RecyclerView.Adapter<TimerInformationAdapter.ItemViewHolder>()
{

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_information_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.button.text = "345"

        holder.button.setOnClickListener {
            val action = TimerListFragmentDirections.actionTimerListFragmentToTimerFragment(timer = "123")
            holder.view.findNavController().navigate(action)
        }
    }
}