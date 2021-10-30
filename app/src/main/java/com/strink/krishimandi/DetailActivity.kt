package com.strink.krishimandi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.strink.krishimandi.adapter.MandiAdapter
import com.strink.krishimandi.databinding.ActivityDetailBinding
import com.strink.krishimandi.db.MandiDatabase
import com.strink.krishimandi.model.OtherMandi
import com.strink.krishimandi.viewmodels.MainViewModel
import com.strink.krishimandi.viewmodels.MainViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mandiList: List<OtherMandi>
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, MandiDatabase::class.java,"mandiDB").build()
        val dbDao = db.mandiDao()


        runBlocking {
            async {
                mandiList = dbDao.getMandi()
            }.await()
        }

        if(mandiList.isEmpty()) {
            binding.recycler.visibility = View.INVISIBLE
            binding.fetchButton.visibility = View.VISIBLE
            binding.fetchButton.setOnClickListener {
                val repository = (application as MandiApplication).mandiRepository
                mainViewModel = ViewModelProvider(
                    this,
                    MainViewModelFactory(repository)
                ).get(MainViewModel::class.java)

                mainViewModel.mandi.observe(this, Observer {
                    mandiList = it.data.other_mandi
                    Log.d("Retrofit Call", it.data.other_mandi.toString())
                    layoutManager = LinearLayoutManager(applicationContext)
                    val adapter = MandiAdapter(mandiList)
                    binding.recycler.layoutManager = layoutManager
                    binding.recycler.adapter = adapter
                })
                binding.fetchButton.visibility = View.INVISIBLE
                binding.recycler.visibility = View.VISIBLE
            }

        } else {
            binding.recycler.visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(applicationContext)
            val adapter = MandiAdapter(mandiList)
            binding.recycler.layoutManager = layoutManager
            binding.recycler.adapter = adapter
        }
    }
}