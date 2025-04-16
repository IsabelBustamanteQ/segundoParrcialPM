package com.ucb.ucbtest.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBooksUI(
    bookViewModel: BookViewModel = hiltViewModel(),
    navController: NavHostController,
    onBackPressed: () -> Unit
) {
    val uiState by bookViewModel.flow.collectAsState()

    if (uiState is BookViewModel.BookState.Init) {
        LaunchedEffect(Unit) {
            bookViewModel.getBooks()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Libros Favoritos")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                when (val ui = uiState) {
                    is BookViewModel.BookState.Init -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Text(
                                "Iniciando Lista de Libros guardados",
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }

                    is BookViewModel.BookState.Successful -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Resultados:",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(1),
                                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 16.dp)
                            ) {
                                items(ui.list) { book ->
                                    ElevatedCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text("📖 ${book.title}", style = MaterialTheme.typography.titleLarge)
                                            Text("👤 Autor(es): ${book.author.joinToString()}")
                                            Text("📅 Año de publicación: ${book.publish_year}")
                                        }
                                    }
                                }
                            }
                        }
                    }

                    is BookViewModel.BookState.Error -> {
                        Text(ui.message)
                    }

                    is BookViewModel.BookState.Saved -> {
                        //
                    }
                }
            }
        }
    )
}
