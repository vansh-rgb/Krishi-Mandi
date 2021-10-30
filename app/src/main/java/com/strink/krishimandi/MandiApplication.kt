package com.strink.krishimandi

import android.app.Application
import com.strink.krishimandi.api.MandiService
import com.strink.krishimandi.api.RetrofitHelper
import com.strink.krishimandi.db.MandiDatabase
import com.strink.krishimandi.repository.MandiRepository

class MandiApplication: Application() {

    lateinit var mandiRepository: MandiRepository

    override fun onCreate() {
        super.onCreate()
        val mandiService = RetrofitHelper.getInstance().create(MandiService::class.java)
        val database = MandiDatabase.getDatabase(applicationContext)
        mandiRepository = MandiRepository(mandiService, database)
    }
}