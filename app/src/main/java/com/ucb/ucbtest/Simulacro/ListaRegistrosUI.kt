package com.ucb.ucbtest.Simulacro

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ucb.domain.Movie
import com.ucb.ucbtest.R
import com.ucb.ucbtest.movie.MovieViewModel

@Composable
fun ListaRegistroUI(finanzasViewModel: FinanzasViewModel = hiltViewModel(),navController: NavHostController, onBackPressed: () -> Unit) {
//    val finanzasViewModel: FinanzasViewModel = viewModel()

    LaunchedEffect(Unit) {
        finanzasViewModel.getRegistros()
    }

    val uiState by finanzasViewModel.finanzasState.collectAsState()

    when ( val ui = uiState) {
        is FinanzasViewModel.FinanzasState.Init -> {
            CircularProgressIndicator()
            Column(
                modifier = Modifier.padding(top = 40.dp)
            ){
                Text("Iniciando Lista de Registros")
            }
        }
        is FinanzasViewModel.FinanzasState.Loaded -> {
            Column(
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Lista de Ingresos y Egresos" ,
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
                        val registro = ui.list[index]

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
                                Text(text = "Nombre: ${registro.nombre}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Precio: ${registro.precio}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Descripción: ${registro.descripcion}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Fecha: ${registro.fecha}", style = MaterialTheme.typography.bodySmall)

                                Text(
                                    text = "Borrar",
                                    color = Color.Red,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .clickable {
                                            finanzasViewModel.deleteFinanza(registro.nombre)
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
        is FinanzasViewModel.FinanzasState.Error -> {
            Text(ui.message)
        }
        FinanzasViewModel.FinanzasState.Loading -> {
            LaunchedEffect(Unit) {
                finanzasViewModel.getRegistros()
            }
        }
        is FinanzasViewModel.FinanzasState.Successful -> {
            LaunchedEffect(Unit) {
                finanzasViewModel.getRegistros()
            }
        }
    }

}