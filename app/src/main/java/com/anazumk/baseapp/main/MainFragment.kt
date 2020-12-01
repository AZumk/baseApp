package com.anazumk.baseapp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anazumk.baseapp.R
import com.anazumk.baseapp.network.model.RegionalData
import com.anazumk.baseapp.utils.DateParser
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainFragment : Fragment(), RecyclerViewListener {

    companion object {
        val Tag = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(context)

        setObservers()
        setViewListeners()
        if(viewModel.regionsData.value == null) viewModel.getRegionalCarbonIntensity()
    }

    private fun setObservers(){
        viewModel.regionsData.observe(viewLifecycleOwner, Observer {
            it?.let {
                headerDay.text = getDate(it.to)
                headerTime.text = getString(R.string.header_time).format(getTime(it.from), getTime(it.to))
                val adapter = MainListAdapter(it, this)
                recyclerview.adapter = adapter
            }
        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer {
            errorLayout.visibility = if(it == null) View.GONE else View.VISIBLE
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            loadingLayout.visibility = if(it) View.VISIBLE else View.GONE
        })
    }

    private fun setViewListeners(){
        tryAgainButton.setOnClickListener {
            viewModel.loadingError.postValue(null)
            viewModel.getRegionalCarbonIntensity()
        }
    }

    private fun getTime(date: Date?): String =
        date?.let {
            DateParser.format(it, DateParser.FormatType.HourMinute)
        } ?: ""

    private fun getDate(date: Date?): String =
        date?.let {
            DateParser.format(it, DateParser.FormatType.DayMonthYearSlashed)
        } ?: ""

    override fun onItemClicked(item: RegionalData) {
        (activity as? MainActivity)?.openDetail(item)
    }
}
