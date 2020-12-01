package com.anazumk.baseapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anazumk.baseapp.network.NetworkHelper
import com.anazumk.baseapp.network.model.CustomError
import com.anazumk.baseapp.network.model.RegionalData
import com.anazumk.baseapp.network.model.Regions

class MainViewModel : ViewModel() {

    val regionsData = MutableLiveData<Regions>()
    val loadingError =  MutableLiveData<CustomError>()
    val isLoading = MutableLiveData<Boolean>()

    fun getRegionalCarbonIntensity() {

        isLoading.postValue(true)

       val call = NetworkHelper.getAPI().getRegionalIndex()
        NetworkHelper.execute(call, {
            it?.data?.first()?.let { regions ->
                regionsData.postValue(regions)
            } ?: loadingError.postValue(CustomError(0, "Bad data received"))

            isLoading.postValue(false)
        }, {
            loadingError.postValue(it)
            isLoading.postValue(false)
        })
    }
}
