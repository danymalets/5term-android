package com.example.timer.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.adapter.SequenceAdapter
import com.example.timer.databinding.FragmentSequenceListBinding
import com.example.timer.model.Sequence
import com.example.timer.service.TimerService
import com.example.timer.viewmodel.SequenceListViewModel
import android.content.Intent


class SequenceListFragment : Fragment() {

    private var _binding: FragmentSequenceListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val sequenceViewModel: SequenceListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSequenceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val timerAdapter = SequenceAdapter(requireContext(), sequenceViewModel)
        recyclerView.adapter = timerAdapter

        binding.floatingActionButton.setOnClickListener{
            val action = SequenceListFragmentDirections.actionSequenceListFragmentToEditSequenceFragment(
                Sequence(
                    0,
                    "New Timer",
                    Color.WHITE,
                    10,
                    5,
                    3,
                    4,
                    13
                ),
                true
            )
            findNavController().navigate(action)
        }

        sequenceViewModel.allTimers.observe(viewLifecycleOwner, { timerList ->
            timerAdapter.setData(timerList)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_settings -> {
                val action = SequenceListFragmentDirections.actionSequenceListFragmentToSettingsFragment()
                findNavController().navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}