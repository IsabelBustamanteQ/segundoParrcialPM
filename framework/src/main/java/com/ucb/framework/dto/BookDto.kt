package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookDto(
    @Json(name = "author_name")
    val author_name: List<String> = emptyList(),

    @Json(name = "title")
    val title: String = "",

    @Json(name = "first_publish_year")
    val first_publish_year: String = ""
)
