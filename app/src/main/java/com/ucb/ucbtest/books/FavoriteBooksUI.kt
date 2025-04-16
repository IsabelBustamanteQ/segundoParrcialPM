package com.ucb.ucbtest.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ucb.domain.Movie
import com.ucb.domain.books.Book
import com.ucb.ucbtest.Simulacro.FinanzasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBooksUI() {
    val bookViewModel: BookViewModel = viewModel()

    LaunchedEffect(Unit) {
        bookViewModel.getBooks()
    }

    val uiState by bookViewModel.flow.collectAsState()

    when ( val ui = uiState) {
        is BookViewModel.BookState.Init -> {
            CircularProgressIndicator()
            Column(
                modifier = Modifier.padding(top = 40.dp)
            ){
                Text("Iniciando Lista de Libros guardados")
            }
        }
        is BookViewModel.BookState.Successful -> {
            Column(
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Libros Favoritos" ,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center)
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    //.padding(top = 100.dp),
                    columns = GridCells.Fixed(1) // GridCells.Adaptive(minSize =  128.dp),


                ) {
                    items(ui.list.size) { index ->
                        val book = ui.list[index]

                        ElevatedCard(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = Color(0xFFE3F2FD))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text(text = "Titulo: ${book.title}", style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "Autor(es):"
                                )
                                book.author.forEach { author ->
                                    Text(text = author)
                                }
                                Text(
                                    text = "Año Publicación: ",
                                )
                                Text(
                                    text = book.publish_year,
                                )
                            }
                        }
                    }
                }
            }
        }
        is BookViewModel.BookState.Error -> {
            Text(ui.message)
        }

        is BookViewModel.BookState.Saved ->{}
    }

}