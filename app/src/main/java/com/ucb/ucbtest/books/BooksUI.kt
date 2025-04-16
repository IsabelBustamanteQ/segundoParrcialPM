package com.ucb.ucbtest.books

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.ucb.ucbtest.R
import com.ucb.ucbtest.Simulacro.FinanzasViewModel
import com.ucb.ucbtest.navigation.Screen

@Composable
fun BooksUI(viewModel: BookViewModel = hiltViewModel(), navController: NavHostController, onSuccess : () -> Unit) {
    var bookName by remember { mutableStateOf("") }

    val bookState by viewModel.flow.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Toast.makeText(context,"Cargando Libros Favoritos",Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.FavoriteBooksScreen.route)
                }
            ) {
                Text("Libros Favoritos")
            }
            Text(
                "Bienvenido al Buscador de Libro"
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = bookName,
                onValueChange = {
                    bookName = it
                },
                label = {
                    Text("Ingrese el nombre del libro")
                }
            )
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.fetchBooks(bookName)
                }
            ) {
                Text(stringResource(id = R.string.gitalias_btn_find))
            }
            when( val state = bookState) {
                is BookViewModel.BookState.Init -> {
                    Text("Aún no se buscó nada")
                }
                is BookViewModel.BookState.Successful -> {
                    Column(
                        modifier = Modifier.padding(top = 40.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Book List")
                        }
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize(),
                            //.padding(top = 100.dp),
                            columns = GridCells.Fixed(1) // GridCells.Adaptive(minSize =  128.dp),

                        ) {
                            items(state.list) {book->
                                ElevatedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 8.dp
                                        )
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(color = Color.Cyan)
                                ) {
                                    Box( modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.BottomCenter)
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        )  {
                                            Text(
                                                text = "Título:",
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.padding(horizontal = 8.dp),
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = book.title,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.padding(horizontal = 8.dp),
                                                textAlign = TextAlign.Center
                                            )
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
                                            OutlinedButton(
                                                modifier = Modifier.fillMaxWidth(),
                                                onClick = {
                                                    viewModel.savingBook(book)
                                                }
                                            ){
                                                Text("Me gusta")
                                        }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                is BookViewModel.BookState.Error -> {
                    Text(state.message)
                }

                is BookViewModel.BookState.Saved -> {
                    Toast.makeText(context,"Se añadió a libros favoritos",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}