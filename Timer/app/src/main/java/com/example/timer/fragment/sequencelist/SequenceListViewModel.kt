package com.example.timer.fragment.sequencelist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.database.SequenceDatabase
import com.example.timer.model.Sequence
import com.example.timer.repository.SequenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.List

class SequenceListViewModel(application: Application) : AndroidViewModel(application) {

    val allTimers: LiveData<List<Sequence>>
    private val sequenceRepository: SequenceRepository

    init{
        val timerDao = SequenceDatabase.getDatabase(application).sequenceDao()
        sequenceRepository = SequenceRepository(timerDao)
        allTimers = sequenceRepository.getAllTimers()
    }

    fun addSequence(sequence: Sequence){
        viewModelScope.launch(Dispatchers.IO){
            sequenceRepository.addSequence(sequence)
        }
    }

    fun updateSequence(sequence: Sequence){
        viewModelScope.launch(Dispatchers.IO){
            sequenceRepository.updateSequence(sequence)
        }
    }

    fun deleteSequence(sequence: Sequence){
        viewModelScope.launch(Dispatchers.IO){
            sequenceRepository.deleteSequence(sequence)
        }
    }

    fun deleteAllSequence(){
        viewModelScope.launch(Dispatchers.IO){
            sequenceRepository.deleteAllSequences()
        }
    }
}