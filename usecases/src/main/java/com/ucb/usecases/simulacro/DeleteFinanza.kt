package com.ucb.usecases.simulacro

import com.ucb.data.FinanzasRepository


class DeleteFinanza(val repository: FinanzasRepository) {
    suspend fun invoke(name: String):Boolean{
        repository.deleteRegistro(name)
        return true
    }
}