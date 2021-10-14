package com.example.timer.fragment.timer

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timer.adapter.TimerAdapter
import com.example.timer.databinding.FragmentTimerBinding
import com.example.timer.viewmodel.TimerViewModel
import androidx.navigation.fragment.navArgs
import com.example.timer.R
import com.example.timer.fragment.timer.adapter.TimerStatus
import com.example.timer.model.Sequence
import com.example.timer.model.Timer
import com.example.timer.viewmodel.TimerViewModelFactory


class TimerFragment : Fragment() {

    private val args: TimerFragmentArgs by navArgs()

    private val timerList: Array<Timer> by lazy { toTimerList(args.sequence) }

    private val viewModelFactory: TimerViewModelFactory by lazy {
        TimerViewModelFactory(requireActivity().application, args.sequence, timerList, args.firstInit)
    }

    private val timerViewModel: TimerViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentTimerBinding? = null

    private val binding get() = _binding!!
    private lateinit var timerStatus: TimerStatus

    private var exit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d("oncreated", "oncreated0")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val timerAdapter = TimerAdapter(requireContext(), timerList)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = timerAdapter

        binding.root.setBackgroundColor(args.sequence.color)

        binding.textTimerTitle.text = timerList[0].duration.toString()


        timerViewModel.timerObserver.remainingTime.observe(viewLifecycleOwner, {
            binding.textRemainmingTime.text = it.toString()
        })

        timerViewModel.timerObserver.title.observe(viewLifecycleOwner, {
            binding.textTimerTitle.text = it.toString()
        })

        timerViewModel.timerObserver.timerIndex.observe(viewLifecycleOwner, {
            timerAdapter.setSelected(it)
        })

        timerViewModel.timerObserver.status.observe(viewLifecycleOwner, {
            if (it == TimerStatus.RUN){
                binding.buttonPause.setImageDrawable(requireContext().getDrawable(R.drawable.ic_baseline_pause_24))
                timerStatus = TimerStatus.RUN
            }
            else{
                binding.buttonPause.setImageDrawable(requireContext().getDrawable(R.drawable.ic_baseline_play_arrow_24))
                timerStatus = TimerStatus.PAUSE
            }
        })

        binding.buttonPause.setOnClickListener {
            if (timerStatus == TimerStatus.RUN){
                timerViewModel.stop()
                Log.d("click stop", "IIIIIIIIIIIIIII")

            }
            else{
                timerViewModel.start()
            }
        }

        binding.buttonPrevious.setOnClickListener{
            timerViewModel.previousTimer()
        }

        binding.buttonNext.setOnClickListener{
            timerViewModel.nextTimer()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d("onstart", "onstart")
        timerViewModel.stopNotification()
    }

    override fun onStop() {
        super.onStop()
        Log.d("onstop", "onstop")
        if (!exit)
            timerViewModel.startNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.timer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_delete -> {
                timerViewModel.deleteTimer()
                exit = true
                val action = TimerFragmentDirections.actionTimerFragmentToSequenceListFragment()
                findNavController().navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toTimerList(sequence: Sequence): Array<Timer> {
        val list = mutableListOf(Timer(requireContext().getString(R.string.warm_up), sequence.warmUp))
        for (i in 1 until sequence.cycles){
            list.add(Timer(requireContext().getString(R.string.workout), sequence.workout))
            list.add(Timer(requireContext().getString(R.string.rest), sequence.rest))
        }
        list.add(Timer(requireContext().getString(R.string.workout), sequence.workout))
        list.add(Timer(requireContext().getString(R.string.cooldown), sequence.cooldown))
        return list.toTypedArray()
    }
}