package com.fulmen.nasaroom.ui.detail

import androidx.lifecycle.*
import com.fulmen.nasaroom.data.repository.NasaRepository
import com.fulmen.nasaroom.util.Resource
import com.senacor.nasamvvm.data.model.ItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val nasaRepository: NasaRepository
) : ViewModel() {

    private val _exist = MutableLiveData<String>()

    fun insertFavorite(itemData: ItemData) = viewModelScope.launch {
        nasaRepository.insertFavorite(itemData.copy(favorite = true))
    }

    private val _isFavoriteItem = _exist.switchMap { id ->
        nasaRepository.isFavorite(id)
    }

    fun isFavorite(link: String) {
        _exist.value = link
    }

    val exist: LiveData<Resource<Boolean>> = _isFavoriteItem

    fun deleteFavorite(link: String) = viewModelScope.launch {
        nasaRepository.deleteFavorite(link)
    }

}

