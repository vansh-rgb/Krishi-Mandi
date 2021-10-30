package com.strink.krishimandi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strink.krishimandi.model.Krishi
import com.strink.krishimandi.repository.MandiRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(private val repository: MandiRepository): ViewModel() {

    init {
        runBlocking {
            async {
                repository.getMandi()
            }.await()
        }
    }

    val mandi: LiveData<Krishi>
    get() = repository.mandi

}