package com.fulmen.nasaroom.ui.favorites

import androidx.lifecycle.ViewModel
import com.fulmen.nasaroom.data.repository.NasaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    nasaRepository: NasaRepository
): ViewModel() {

    val res = nasaRepository.getFavorites()
}