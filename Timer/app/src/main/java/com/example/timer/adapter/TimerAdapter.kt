package com.example.timer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.fragment.SequenceListFragmentDirections
import com.example.timer.model.Sequence
import com.example.timer.model.Timer
import com.example.timer.viewmodel.SequenceListViewModel
import com.google.android.material.card.MaterialCardView

class TimerAdapter(
    private val context: Context,
    private val content: List<Timer>
) : RecyclerView.Adapter<TimerAdapter.ItemViewHolder>()
{

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textTitle: TextView = view.findViewById(R.id.text_title)
        val textDuration: TextView = view.findViewById(R.id.text_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = content.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = content[position]

        holder.textTitle.text = item.title
        holder.textDuration.text = item.duration.toString()
    }
}