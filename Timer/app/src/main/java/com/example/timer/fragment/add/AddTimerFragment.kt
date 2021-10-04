package com.example.timer.fragment.add

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.timer.R
import com.example.timer.databinding.FragmentAddTimerBinding
import com.example.timer.viewmodel.TimerListViewModel
import com.example.timer.viewmodel.TimerViewModel
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver

class AddTimerFragment : Fragment() {

    private var _binding: FragmentAddTimerBinding? = null
    private val binding get() = _binding!!

    private val timerListViewModel: TimerListViewModel by activityViewModels()

    private val timerViewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = timerViewModel

        binding.buttonChangeColor.setOnClickListener{
            ColorPickerPopup.Builder(requireContext())
                .initialColor(Color.WHITE)
                .enableBrightness(true)
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(view, object: ColorPickerObserver(){
                    override fun onColorPicked(color: Int) {
                        view.setBackgroundColor(color)
                        timerViewModel.color.value = color
                    }
                })
        }

        binding.buttonSave.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        timerListViewModel.addTimer(timerViewModel.getTimer())
        Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addNewTimerFragment_to_timerListFragment)
    }
}