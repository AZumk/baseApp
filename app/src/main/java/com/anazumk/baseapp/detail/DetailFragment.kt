package com.anazumk.baseapp.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anazumk.baseapp.R
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
        updateGraph()
        updateView()
    }

    private fun updateView(){
        regionName?.text = regionalData.shortName
        regiondno?.text = regionalData.dnoRegion
        regionIntensityForecast?.text = regionalData.intensity.forecast
        regionIntensityIndex?.text = regionalData.intensity.index.name.toLowerCase()
    }

    private fun updateGraph(){
        val dataSet = PieDataSet(regionalData.generationMix.map { PieEntry(it.percentage.toFloat(), it.fuel) }, "Mix")
        context?.let {
            val colorSet = listOf(
                ContextCompat.getColor(it, R.color.lightGreen),
                ContextCompat.getColor(it, R.color.darkGreen),
                ContextCompat.getColor(it, R.color.nunclearGray),
                ContextCompat.getColor(it, R.color.charcoal))

            dataSet.colors = colorSet
        }

        mixPie.holeRadius = 90f
        mixPie.data = PieData(dataSet)
    }
}