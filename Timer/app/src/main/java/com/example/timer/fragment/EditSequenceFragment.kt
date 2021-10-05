package com.example.timer.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.timer.databinding.FragmentEditSequenceBinding
import com.example.timer.viewmodel.SequenceListViewModel
import com.example.timer.viewmodel.EditSequenceViewModel
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver

class EditSequenceFragment : Fragment() {

    private var _binding: FragmentEditSequenceBinding? = null
    private val binding get() = _binding!!

    private val sequenceListViewModel: SequenceListViewModel by activityViewModels()

    private val editSequenceViewModel: EditSequenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("fr", "start")
        _binding = FragmentEditSequenceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        EditSequenceFragmentArgs.fromBundle(requireArguments()).apply {
            editSequenceViewModel.sequence = sequence
            editSequenceViewModel.new = new
        }

        binding.apply {
            editSequenceViewModel.sequence.apply {
                editTextTitle.setText(title)
                root.setBackgroundColor(color)
                editTextWarmUp.setText(warmUp.toString())
                editTextWorkout.setText(workout.toString())
                editTextRest.setText(rest.toString())
                editTextCycles.setText(cycles.toString())
                editTextCooldown.setText(cooldown.toString())

                editTextTitle.addTextChangedListener { text -> title = text.toString() }
                editTextWarmUp.addTextChangedListener { text -> warmUp = text.toString().toIntOrNull()?:0}
                editTextWorkout.addTextChangedListener { text -> workout = text.toString().toIntOrNull()?:0 }
                editTextRest.addTextChangedListener { text -> rest = text.toString().toIntOrNull()?:0 }
                editTextCycles.addTextChangedListener { text -> cycles = text.toString().toIntOrNull()?:0 }
                editTextCooldown.addTextChangedListener { text -> cooldown = text.toString().toIntOrNull()?:0 }
            }
        }

        binding.buttonChangeColor.setOnClickListener{
            ColorPickerPopup.Builder(requireContext())
                .initialColor(Color.WHITE)
                .enableBrightness(false)
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(view, object: ColorPickerObserver(){
                    override fun onColorPicked(color: Int) {
                        editSequenceViewModel.sequence.color = color
                        view.setBackgroundColor(color)
                    }
                })
        }

        binding.buttonSave.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        if (editSequenceViewModel.new){
            sequenceListViewModel.addSequence(editSequenceViewModel.sequence)
            Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show()
        }
        else{
            sequenceListViewModel.updateSequence(editSequenceViewModel.sequence)
            Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()
        }
        val action = EditSequenceFragmentDirections.actionEditSequenceFragmentToSequenceListFragment()
        findNavController().navigate(action)
    }
}