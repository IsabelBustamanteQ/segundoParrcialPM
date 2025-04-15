package com.ucb.usecases.simulacro

import com.ucb.data.FinanzasRepository
import com.ucb.domain.simulacro.Finanza

class GetRegistros(val repository: FinanzasRepository) {
    suspend fun invoke():List<Finanza>{
        return repository.getRegistros()
    }
}