package com.ucb.framework.service

import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.BookResponseDto
import com.ucb.framework.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IBookApiService {
    @GET("/search.json")
    suspend fun fetchBooks(@Query("q") bookName: String): Response<BookResponseDto>
}
