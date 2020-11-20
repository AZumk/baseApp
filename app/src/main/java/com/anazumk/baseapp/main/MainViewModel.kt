package com.anazumk.baseapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anazumk.baseapp.network.NetworkHelper
import com.anazumk.baseapp.network.model.CustomError
import com.anazumk.baseapp.network.model.RegionalData
import com.anazumk.baseapp.network.model.Regions

class MainViewModel : ViewModel() {

    val regionsData = MutableLiveData<Regions>()
    val gbData = MutableLiveData<RegionalData>()
    val loadingError =  MutableLiveData<CustomError>()
    val isLoading = MutableLiveData<Boolean>()

    fun getCall() {
        isLoading.postValue(true)
       val call = NetworkHelper.getAPI().getRegionalIndex()
        NetworkHelper.execute(call, {
            it?.data?.first()?.let {regions ->
                regionsData.postValue(regions)
                gbData.postValue(regions.regionsList.find { region -> region.regionId == 18 })
            }
            isLoading.postValue(false)
        }, {
            loadingError.postValue(it)
            isLoading.postValue(false)
        })
    }
}
