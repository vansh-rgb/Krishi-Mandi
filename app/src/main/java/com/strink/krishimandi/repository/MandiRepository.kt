package com.strink.krishimandi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.strink.krishimandi.api.MandiService
import com.strink.krishimandi.db.MandiDao
import com.strink.krishimandi.db.MandiDatabase
import com.strink.krishimandi.model.Krishi


class MandiRepository(private val mandiService: MandiService, private val mandiDatabase: MandiDatabase) {

    private val mandiLiveData = MutableLiveData<Krishi>()

    val mandi: LiveData<Krishi>
    get() = mandiLiveData

    suspend fun getMandi(){
        val result = mandiService.getMandi()
        if(result != null) {
            mandiDatabase.mandiDao().addMandi(result.body()!!.data.other_mandi)
            mandiLiveData.postValue(result.body())
        }
    }
}