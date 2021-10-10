package com.example.timer.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timer.adapter.TimerAdapter
import com.example.timer.databinding.FragmentTimerBinding
import com.example.timer.viewmodel.TimerViewModel
import androidx.navigation.fragment.navArgs
import com.example.timer.service.NotificationUtil
import com.example.timer.viewmodel.TimerViewModelFactory


class TimerFragment : Fragment() {

    private val args: TimerFragmentArgs by navArgs()

    private val viewModelFactory: TimerViewModelFactory by lazy {
        TimerViewModelFactory(requireActivity().application, args.sequence, args.firstInit)
    }
    private val timerViewModel: TimerViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.d("act", "back")
        }
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
        val timerList = TimerViewModel.toTimerList(sequence)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TimerAdapter(context, timerList)
        }
        binding.root.setBackgroundColor(sequence.color)

        binding.textTimerTitle.text = timerList[0].duration.toString()

        timerViewModel.onForeground()

        timerViewModel.duration.observe(viewLifecycleOwner, {
            binding.textTimerDuration.text = it.toString()
        })
    }

    override fun onStop() {
        super.onStop()
        timerViewModel.onBackground()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("act", "destroy")
    }


    companion object{
        const val WARM_UP = "Warm-up"
        const val WORKOUT = "Workout"
        const val REST = "Rest"
        const val COOLDOWN = "Cooldown"
    }

}