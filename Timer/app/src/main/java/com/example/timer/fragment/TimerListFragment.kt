package com.example.timer.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.adapter.TimerAdapter
import com.example.timer.databinding.FragmentTimerListBinding
import com.example.timer.model.TimerViewModel

class TimerListFragment : Fragment() {

    private var _binding: FragmentTimerListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val timerViewModel: TimerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val timerAdapter = TimerAdapter()
        recyclerView.adapter = timerAdapter

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_timerListFragment_to_addNewTimerFragment)
        }

        timerViewModel.allTimers.observe(viewLifecycleOwner, { timerList ->
            timerAdapter.setData(timerList)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_settings -> {
                findNavController().navigate(R.id.action_timerListFragment_to_settingsFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}