package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BookResponseDto(
    @Json(name = "numFound")
    val numFound: Int,
    @Json(name = "docs")
    val docs: List<BookDto>
)