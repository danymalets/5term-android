package com.example.timer

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.adapter.TimerInformationAdapter
import com.example.timer.databinding.FragmentTimerListBinding
import com.example.timer.model.TimerInformation

class TimerListFragment : Fragment() {

    private var _binding: FragmentTimerListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

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
        recyclerView.adapter = TimerInformationAdapter(this, listOf(TimerInformation(1,1,1,1,1)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_settings -> {
                val action = TimerListFragmentDirections.actionTimerListFragmentToSettingsFragment()
                binding.root.findNavController().navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}