package com.senacor.nasamvvm.data.model

data class NasaData(
    val status: String?,
    val feed: FeedData?,
    val items: List<ItemData>
)
