package com.ucb.usecases.simulacro

import com.ucb.data.FinanzasRepository
import com.ucb.domain.simulacro.Finanza
import kotlinx.coroutines.delay

class DoIngreso(
    private val finanzasRepository: FinanzasRepository
) {
    suspend fun invoke(finanza: Finanza) : Boolean {
        delay(1)
        return finanzasRepository.doIngreso(finanza)
    }
}