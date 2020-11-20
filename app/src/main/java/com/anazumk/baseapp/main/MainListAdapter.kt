package com.anazumk.baseapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anazumk.baseapp.R
import com.anazumk.baseapp.network.model.CarbonIndex
import com.anazumk.baseapp.network.model.RegionalData
import com.anazumk.baseapp.network.model.Regions
import kotlinx.android.synthetic.main.item_region_carbon.view.*

interface RecyclerViewListener {
    fun onItemClicked(item: RegionalData)
}

class MainListAdapter(data: Regions, var listener: RecyclerViewListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItems: List<Any>? = null

    init {
        listItems = data.regionsList.reversed()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RegionViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_region_carbon, parent, false))
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as?  RegionViewHolder)?.run {
            (listItems?.get(position) as? RegionalData)?.let {
                updateText(it)
                itemView.setOnClickListener {
                    listener.onItemClicked(listItems?.get(position) as RegionalData)
                }
            }
        }
    }

}

class RegionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    fun updateText(data: RegionalData){
        itemView.regionTitle?.text = data.shortName
        itemView.forecastIndex?.text = data.intensity.index
        itemView.forecastValue?.text = data.intensity.forecast

        itemView.forecastIndexIndicator.background = ContextCompat.getDrawable(itemView.context,
            when(CarbonIndex.from(data.intensity.index)){
                CarbonIndex.LOW, CarbonIndex.VERY_LOW -> { R.color.lightMoss }
                CarbonIndex.MODERATE -> { R.color.orange }
                else -> { R.color.red }
            })
    }
}