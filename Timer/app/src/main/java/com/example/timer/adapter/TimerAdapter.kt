package com.example.timer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.model.Sequence
import com.example.timer.viewmodel.TimerListViewModel

class TimerAdapter(
    private val context: Context,
    private val timerViewModel: TimerListViewModel
) : RecyclerView.Adapter<TimerAdapter.ItemViewHolder>()
{
    private var timerList = emptyList<Sequence>()

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val buttonPlay: ImageButton = view.findViewById(R.id.button_play)
        val buttonEdit: ImageButton = view.findViewById(R.id.button_edit)
        val buttonDelete: ImageButton = view.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = timerList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = timerList[position]


        holder.buttonPlay.setOnClickListener {
            holder.view.findNavController().navigate(R.id.action_timerListFragment_to_timerFragment)
        }
        holder.buttonEdit.setOnClickListener {
            holder.view.findNavController().navigate(R.id.action_timerListFragment_to_editTimerFragment)
        }
        holder.buttonDelete.setOnClickListener {
            timerViewModel.deleteTimer(item)
            notifyDataSetChanged()
            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setData(timerList: kotlin.collections.List<Sequence>){
        this.timerList = timerList
        notifyDataSetChanged()
    }
}