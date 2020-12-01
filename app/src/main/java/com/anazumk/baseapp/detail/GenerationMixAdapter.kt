package com.anazumk.baseapp.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anazumk.baseapp.R
import com.anazumk.baseapp.network.model.GenerationMix
import kotlinx.android.synthetic.main.item_generation_mix.view.*

class GenerationMixAdapter(private val generationMix: List<GenerationMix>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GenerationMixViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_generation_mix, parent, false))


    override fun getItemCount(): Int = generationMix.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? GenerationMixViewHolder)?.updateView(generationMix[position])
    }
}

class GenerationMixViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    fun updateView(mix: GenerationMix){
        itemView.fuelType.text = mix.fuel
        itemView.fuelPercent.text = mix.percentage.toString()
        itemView.fuelPercentBar.progress = mix.percentage.toInt()
    }
}