package com.ucb.usecases.books

import com.ucb.data.BookRepository
import com.ucb.domain.books.Book

class GetBooks(val repository: BookRepository) {
    suspend fun invoke():List<Book>{
        return repository.getBooks()
    }
}
