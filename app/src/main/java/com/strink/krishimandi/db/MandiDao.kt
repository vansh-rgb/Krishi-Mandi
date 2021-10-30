package com.strink.krishimandi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.strink.krishimandi.model.OtherMandi

@Dao
interface MandiDao {

    @Insert
    suspend fun addMandi(mandi: List<OtherMandi>)

    @Query("SELECT * FROM mandi")
    suspend fun getMandi(): List<OtherMandi>

}