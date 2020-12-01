package com.anazumk.baseapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anazumk.baseapp.R
import com.anazumk.baseapp.network.model.GenerationMix
import com.anazumk.baseapp.network.model.RegionalData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    companion object {
        val Tag = DetailFragment::class.java.simpleName
        const val ArgRegionalData = "regionalData"

        fun newInstance(regionalData: RegionalData) : DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(ArgRegionalData, regionalData)
            }
            return fragment
        }
    }

    private lateinit var regionalData: RegionalData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        regionalData = arguments?.getSerializable(ArgRegionalData) as? RegionalData ?: throw Exception("Error loading regional data")
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        generationMixRecyclerView.layoutManager = LinearLayoutManager(context)
        generationMixRecyclerView.adapter = GenerationMixAdapter(regionalData.generationMix.sortedBy { it.percentage }.reversed())

        updateGraph()
        updateView()
    }

    private fun updateView(){
        regionName?.text = regionalData.shortName
        regiondno?.text = regionalData.dnoRegion
        regionIntensityForecast?.text = regionalData.intensity.forecast
        regionIntensityIndex?.text = regionalData.intensity.index
    }

    private fun processPercentages(mix: List<GenerationMix>): List<PieEntry> {
        val list = mutableListOf<PieEntry>()
        var othersValue = 0f
        mix.forEach { if(it.percentage < 10f) othersValue += it.percentage else list.add(PieEntry(it.percentage, it.fuel)) }
        list.add(PieEntry(othersValue, "others"))
        return list
    }

    private fun updateGraph(){
        val dataSet = PieDataSet(processPercentages(regionalData.generationMix), "Mix")

        context?.let {
            val colorSet = listOf(
                ContextCompat.getColor(it, R.color.lightGreen),
                ContextCompat.getColor(it, R.color.darkGreen),
                ContextCompat.getColor(it, R.color.lightMoss),
                ContextCompat.getColor(it, R.color.nunclearGray),
                ContextCompat.getColor(it, R.color.darkMoss),
                ContextCompat.getColor(it, R.color.darkMoss2),
                ContextCompat.getColor(it, R.color.orange),
                ContextCompat.getColor(it, R.color.darkOrange),
                ContextCompat.getColor(it, R.color.red),
                ContextCompat.getColor(it, R.color.mediumRed)
            )

            dataSet.apply {
                valueTextColor = ContextCompat.getColor(it, R.color.charcoal)
                valueTextSize = 16f
                colors = colorSet
            }

            mixPie.isRotationEnabled = false

            mixPie.legend.apply {
                textColor = ContextCompat.getColor(it, R.color.charcoal)
                textSize = 14f
            }
        }

        mixPie.holeRadius = 40f
        mixPie.data = PieData(dataSet)
    }
}