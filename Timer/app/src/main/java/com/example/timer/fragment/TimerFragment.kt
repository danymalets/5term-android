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
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.adapter.TimerAdapter
import com.example.timer.databinding.FragmentTimerBinding
import com.example.timer.service.TimerService
import com.example.timer.viewmodel.TimerViewModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import com.example.timer.model.Timer


class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private val timerViewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timerViewModel.sequence = TimerFragmentArgs.fromBundle(requireArguments()).sequence
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TimerAdapter(context, timerViewModel.toTimerList())
        }

        binding.root.setBackgroundColor(timerViewModel.sequence.color)

    }

    override fun onStop() {
        super.onStop()
        Log.d("act", "stop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("act", "destroy")
    }
}