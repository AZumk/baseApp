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
                hideDivider(position == listItems?.lastIndex)
            }
        }
    }

}

class RegionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun updateText(data: RegionalData){
        itemView.regionTitle?.text = data.shortName
        itemView.forecastIndex?.text = data.intensity.index.name
        itemView.forecastValue?.text = data.intensity.forecast

        itemView.forecastIndexIndicator.background = ContextCompat.getDrawable(itemView.context,
            when(data.intensity.index){
                CarbonIndex.LOW, CarbonIndex.VERY_LOW, CarbonIndex.MODERATE -> { R.drawable.green_gradient_bg }
                else -> { R.drawable.green_gradient_bg }
            })
    }

    fun changeBackgroundColor(isBlue: Boolean){
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, if(isBlue) R.color.lightGreen else R.color.white))
    }

    fun hideDivider(isLast: Boolean){
        itemView.divider.visibility = if(isLast) View.INVISIBLE else View.VISIBLE
    }
}