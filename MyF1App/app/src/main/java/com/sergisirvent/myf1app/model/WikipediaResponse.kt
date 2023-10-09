package com.sergisirvent.myf1app.model

import com.google.gson.annotations.SerializedName

data class WikipediaResponse(
    val query: Query
)

data class Query(
    val pages: Map<String, Page>
)

data class Page(
    val pageid: Int,
    val title: String,
    val thumbnail: Thumbnail?
)

data class Thumbnail(
    val source: String,
    val width: Int,
    val height: Int
)