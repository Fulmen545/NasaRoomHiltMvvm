package com.fulmen.nasaroom.ui.mainlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fulmen.nasaroom.data.repository.NasaRepository
import com.fulmen.nasaroom.util.Resource
import com.senacor.nasamvvm.data.model.NasaData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NasaListViewModel @Inject constructor(
    private val nasaRepository: NasaRepository
): ViewModel() {
    private val _res = MutableLiveData<Resource<NasaData>>()

    val res: LiveData<Resource<NasaData>>
        get() = _res

    fun start() = getDataFromNasa()

    fun getDataFromNasa() = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        nasaRepository.getNasaData().let {
            _res.postValue(it)
        }
    }
}