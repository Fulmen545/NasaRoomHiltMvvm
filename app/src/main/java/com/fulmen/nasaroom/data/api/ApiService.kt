package com.fulmen.nasaroom.data.api

import com.senacor.nasamvvm.data.model.NasaData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api.json?rss_url=https://mars.nasa.gov/rss/blogs.cfm")
    suspend fun getAllData(): Response<NasaData>
}