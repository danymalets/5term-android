package com.example.timer.fragment.editsequence

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timer.model.Sequence

class EditSequenceViewModel: ViewModel() {
    lateinit var sequence: Sequence
    var new: Boolean = false
}