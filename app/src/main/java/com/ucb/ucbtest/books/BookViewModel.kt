package com.ucb.ucbtest.books

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.books.Book
import com.ucb.domain.simulacro.Finanza
import com.ucb.ucbtest.R
import com.ucb.ucbtest.Simulacro.FinanzasViewModel.FinanzasState
import com.ucb.ucbtest.service.InternetConnection
import com.ucb.usecases.books.FindBooks
import com.ucb.usecases.books.SaveBook
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val findBooks : FindBooks,
    private val saveBook: SaveBook,
    @ApplicationContext private val context: Context
): ViewModel() {

    sealed class BookState {
        object Init: BookState()
        data class Successful(val list: List<Book>): BookState()
        data class Error(val message: String): BookState()
        data class Saved(val guardado:Boolean):BookState()
    }
    private val _flow = MutableStateFlow<BookState>(BookState.Init)
    val flow : StateFlow<BookState> = _flow

    private val _saveflow = MutableStateFlow<BookState>(BookState.Init)
    val saveflow : StateFlow<BookState> = _saveflow

    fun fetchBooks(bookName: String) {
        viewModelScope.launch {
            if (InternetConnection.isConnected(context) ) {
                val result = withContext(Dispatchers.IO) { findBooks.invoke(bookName) }
                when (result) {
                    is NetworkResult.Success -> {
                        _flow.value = BookState.Successful(list = result.data.take(50))
                    }
                    is NetworkResult.Error -> {
                        _flow.value = BookState.Error(result.error)
                    }
                }
            } else {
                _flow.value = BookState.Error(context.getString(R.string.internet_conection_error))
            }
        }
    }
    fun savingBook(book:Book){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){saveBook.invoke(book)}
            (if (result){
                BookState.Saved(true)
            } else {
                BookState.Error("No se pudo registrar el ingreso")
            }).also { _saveflow.value = it }
        }
    }
}