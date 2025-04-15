package com.ucb.usecases.simulacro

import com.ucb.data.FinanzasRepository

class GetTotal(val repository: FinanzasRepository) {
    suspend fun invoke():Double{
        var total:Double=0.0
        var registros = repository.getRegistros()
        for(registro in registros){
           total=total+registro.precio.toDouble()
        }
        return total
    }
}