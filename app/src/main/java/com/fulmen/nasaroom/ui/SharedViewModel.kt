package com.fulmen.nasaroom.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senacor.nasamvvm.data.model.ItemData

class SharedViewModel: ViewModel() {
    val detailData = MutableLiveData<ItemData>()

    fun updateData(data: ItemData){
        detailData.value = data
    }
}