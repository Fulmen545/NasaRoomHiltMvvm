package com.fulmen.nasaroom.data.api

import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val apiService: ApiService
    ): BaseDataSource() {

        suspend fun getNasaData() = getResult { apiService.getAllData() }
}