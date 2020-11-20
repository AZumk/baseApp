package com.anazumk.baseapp.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anazumk.baseapp.R
import com.anazumk.baseapp.network.model.RegionalData
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), RecyclerViewListener {

    companion object {
        val Tag = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerview.layoutManager = LinearLayoutManager(context)

        setObservers()
        viewModel.getCall()
    }

    private fun setObservers(){
        viewModel.regionsData.observe(viewLifecycleOwner, Observer {
            it?.let {
                val adapter = MainListAdapter(it, this)
                recyclerview.adapter = adapter
            }
        })

        viewModel.gbData.observe(viewLifecycleOwner, Observer {

        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer {

        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onItemClicked(item: RegionalData) {
        (activity as? MainActivity)?.openDetail(item)
    }
}
