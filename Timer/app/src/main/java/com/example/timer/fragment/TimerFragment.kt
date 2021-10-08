package com.example.timer.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timer.adapter.TimerAdapter
import com.example.timer.databinding.FragmentTimerBinding
import com.example.timer.service.TimerService
import com.example.timer.viewmodel.TimerViewModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import com.example.timer.model.Sequence
import com.example.timer.model.Timer


class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private var receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == TimerService.TICK) {
                val title = intent.getStringExtra(TimerService.TITLE)
                val duration = intent.getIntExtra(TimerService.DURATION, 0)
                binding.textTimerTitle.text = title.toString()
                binding.textTimerDuration.text = duration.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().registerReceiver(receiver, IntentFilter(TimerService.TICK))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sequence = TimerFragmentArgs.fromBundle(requireArguments()).sequence
        val timerList = toTimerList(sequence)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TimerAdapter(context, timerList)
        }
        binding.root.setBackgroundColor(sequence.color)

        TimerService.startTimerIfNotStarted(requireContext(), ArrayList(timerList))

        binding.textTimerTitle.text = timerList[0].duration.toString()
    }

    override fun onStop() {
        super.onStop()
        Log.d("act", "stop")
        requireContext().unregisterReceiver(receiver);
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("act", "destroy")
    }

    private fun toTimerList(sequence: Sequence): List<Timer>{
        val list = mutableListOf(Timer(WARM_UP, sequence.warmUp))
        for (i in 1 until sequence.cycles){
            list.add(Timer(WORKOUT, sequence.workout))
            list.add(Timer(REST, sequence.rest))
        }
        list.add(Timer(WORKOUT, sequence.workout))
        list.add(Timer(COOLDOWN, sequence.cooldown))
        return list
    }

    companion object{
        const val WARM_UP = "Warm-up"
        const val WORKOUT = "Workout"
        const val REST = "Rest"
        const val COOLDOWN = "Cooldown"
    }
}