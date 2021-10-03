package com.example.timer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timer.R
import com.example.timer.adapter.TimerAdapter
import com.example.timer.database.Timer
import com.example.timer.databinding.FragmentAddNewTimerBinding
import com.example.timer.databinding.FragmentTimerListBinding
import com.example.timer.model.TimerViewModel

class AddNewTimerFragment : Fragment() {

    private var _binding: FragmentAddNewTimerBinding? = null
    private val binding get() = _binding!!

    private val timerViewModel: TimerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        timerViewModel.addTimer(Timer(0, 1, 1, 1, 1, 1))
        findNavController().navigate(R.id.action_addNewTimerFragment_to_timerListFragment)
    }
}