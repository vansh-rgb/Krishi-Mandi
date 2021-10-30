package com.strink.krishimandi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.strink.krishimandi.R
import com.strink.krishimandi.model.OtherMandi

class MandiAdapter(private val mandiList: List<OtherMandi>): RecyclerView.Adapter<MandiAdapter.MandiViewHolder>() {
    class MandiViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val mandiImage: ImageView = view.findViewById(R.id.mandiImage)
        val marketName = view.findViewById<TextView>(R.id.marketTV)
        val districtName = view.findViewById<TextView>(R.id.districtTV)
        val stateName = view.findViewById<TextView>(R.id.stateTV)
        val districtId = view.findViewById<TextView>(R.id.districtID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandiViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MandiViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: MandiViewHolder, position: Int) {
        with(holder) {
            marketName.text = "मंडी : " + mandiList[position].market.toString()
            districtName.text = "जिला : " + mandiList[position].district.toString()
            stateName.text = "राज्य : " + mandiList[position].state.toString()
            districtId.text = "District ID: " + mandiList[position].district_id.toString()
            Picasso.get().load(mandiList[position].image).into(mandiImage)
        }
    }

    override fun getItemCount(): Int {
        return mandiList.size
    }

}