package com.strink.krishimandi.api

import com.strink.krishimandi.model.Krishi
import retrofit2.Response
import retrofit2.http.GET

interface MandiService {

    @GET("test/mandi?lat=28.44108136&lon=77.0526054&ver=89&lang=hi&crop_id=10")
    suspend fun getMandi():Response<Krishi>

}