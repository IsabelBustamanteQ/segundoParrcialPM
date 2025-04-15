package com.ucb.ucbtest.Simulacro

import android.content.ClipDescription
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Gitalias
import com.ucb.domain.Movie
import com.ucb.domain.simulacro.Finanza
import com.ucb.framework.simulacroPersistence.FinanzasRegistro
import com.ucb.ucbtest.R
import com.ucb.ucbtest.movie.MovieViewModel.MovieUIState
import com.ucb.ucbtest.service.InternetConnection
import com.ucb.ucbtest.service.Util
import com.ucb.usecases.DoLogin
import com.ucb.usecases.FindGitAlias
import com.ucb.usecases.GetEmailKey
import com.ucb.usecases.ObtainToken
import com.ucb.usecases.SaveGitalias
import com.ucb.usecases.simulacro.DeleteFinanza
import com.ucb.usecases.simulacro.DoEgreso
import com.ucb.usecases.simulacro.DoIngreso
import com.ucb.usecases.simulacro.GetRegistros
import com.ucb.usecases.simulacro.GetTotal
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FinanzasViewModel @Inject constructor(
    private val deleteFinanzas : DeleteFinanza,
    private val doIngreso : DoIngreso,
    private val doEgreso : DoEgreso,
    private val getRegistros: GetRegistros,
    val getTotal: GetTotal,
    @ApplicationContext private val context: Context
): ViewModel() {

    sealed class FinanzasState {
        object Init: FinanzasState()
        object Loading: FinanzasState()
        class Successful: FinanzasState()
        class Error(val message: String): FinanzasState()
        class Loaded(val list: List<Finanza>): FinanzasState()
    }

    private val _finanzasState = MutableStateFlow<FinanzasState>(FinanzasState.Init)
    var finanzasState: StateFlow<FinanzasState> = _finanzasState

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    fun doIngreso(nombre: String, precio: Double, descripcion: String, fecha: String) {
        _finanzasState.value = FinanzasState.Loading
        viewModelScope.launch {
            val finanza = Finanza(nombre, precio, descripcion, fecha)
            val result = withContext(Dispatchers.IO){doIngreso.invoke(finanza)}
            _finanzasState.value = if (result) {
                FinanzasState.Successful()
            } else {
                FinanzasState.Error("No se pudo registrar el ingreso")
            }
        }
    }

    fun doEgreso(nombre: String, precio: Double, descripcion: String, fecha: String) {
        _finanzasState.value = FinanzasState.Loading
        viewModelScope.launch {
            val finanza = Finanza(nombre, -precio, descripcion, fecha)
            val result = withContext(Dispatchers.IO) { doEgreso.invoke(finanza) }

            if (result) {
                val newTotal = withContext(Dispatchers.IO) { getTotal.invoke() }
                _total.value = newTotal

                if (newTotal <= 0.0) {
                    Util.sendNotificatión(context)
                }

                _finanzasState.value = FinanzasState.Successful()
            } else {
                _finanzasState.value = FinanzasState.Error("No se pudo registrar el egreso")
            }
        }
    }


    fun deleteFinanza(nombre: String) {
        _finanzasState.value = FinanzasState.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){deleteFinanzas.invoke(nombre)}
            _finanzasState.value = if (result) {
                FinanzasState.Successful()
            } else {
                FinanzasState.Error("No se pudo borrar el registro")
            }
        }
    }

    fun getRegistros() {
        _finanzasState.value = FinanzasState.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getRegistros.invoke() }
            _finanzasState.value = if (result.isNotEmpty()) {
                FinanzasState.Loaded(result)
            } else {
                FinanzasState.Error("No se encontraron registros")
            }
        }
    }

    fun calcularTotal() {
        _finanzasState.value = FinanzasState.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){getTotal.invoke()}
            _total.value = result
            if (result.isNaN()) {
                _finanzasState.value = FinanzasState.Error("No se pudo obtener el total")
            } else {
                _finanzasState.value = FinanzasState.Successful()
            }
        }
    }
}
