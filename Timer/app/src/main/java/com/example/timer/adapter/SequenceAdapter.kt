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
import com.example.timer.model.Sequence
import com.example.timer.fragment.sequencelist.SequenceListViewModel
import com.google.android.material.card.MaterialCardView
import android.app.AlertDialog
import android.content.SharedPreferences
import com.example.timer.fragment.sequencelist.SequenceListFragmentDirections
import com.example.timer.fragment.timer.adapter.KEY_SEQUENCE
import com.example.timer.fragment.timer.adapter.PREFS_TIMER
import com.google.gson.Gson


class SequenceAdapter(
    private val context: Context,
    private val sequenceViewModel: SequenceListViewModel
) : RecyclerView.Adapter<SequenceAdapter.ItemViewHolder>()
{
    private var sequenceList = emptyList<Sequence>()

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val buttonPlay: ImageButton = view.findViewById(com.example.timer.R.id.button_play)
        val buttonEdit: ImageButton = view.findViewById(com.example.timer.R.id.button_edit)
        val buttonDelete: ImageButton = view.findViewById(com.example.timer.R.id.button_delete)

        val textTitle: TextView = view.findViewById(com.example.timer.R.id.text_title)
        val textWarmUp: TextView = view.findViewById(com.example.timer.R.id.text_warm_up)
        val textWorkout: TextView = view.findViewById(com.example.timer.R.id.text_workout)
        val textRest: TextView = view.findViewById(com.example.timer.R.id.text_rest)
        val textCycles: TextView = view.findViewById(com.example.timer.R.id.text_cycles)
        val textCooldown: TextView = view.findViewById(com.example.timer.R.id.text_cooldown)
        val textTotalDuration: TextView = view.findViewById(com.example.timer.R.id.text_total_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(com.example.timer.R.layout.sequence_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = sequenceList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = sequenceList[position]

        holder.buttonPlay.setOnClickListener {
            val action = SequenceListFragmentDirections.actionSequenceListFragmentToTimerFragment(item, true)
            holder.view.findNavController().navigate(action)
        }
        holder.buttonEdit.setOnClickListener {
            val action = SequenceListFragmentDirections.actionSequenceListFragmentToEditSequenceFragment(item, false)
            holder.view.findNavController().navigate(action)
        }
        holder.buttonDelete.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Do you want to delete this sequence?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    sequenceViewModel.deleteSequence(item)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.setTitle("Confirmation")
            alert.show()
        }

        holder.textTitle.text = item.title
        holder.textWarmUp.text = context.getString(com.example.timer.R.string.sequence_item_warm_up, item.warmUp)
        holder.textWorkout.text = context.getString(com.example.timer.R.string.sequence_item_workout, item.workout)
        holder.textRest.text = context.getString(com.example.timer.R.string.sequence_item_rest, item.rest)
        holder.textCycles.text = context.getString(com.example.timer.R.string.sequence_item_cycles, item.cycles)
        holder.textCooldown.text = context.getString(com.example.timer.R.string.sequence_item_cooldown, item.cooldown)
        holder.textTotalDuration.text = context.getString(com.example.timer.R.string.sequence_item_total_duration, item.totalDuration())

        (holder.view as MaterialCardView).setCardBackgroundColor(item.color)
    }

    fun setData(timerList: List<Sequence>){
        this.sequenceList = timerList
        notifyDataSetChanged()
    }
}