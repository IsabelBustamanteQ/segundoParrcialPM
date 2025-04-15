package com.ucb.data.simulacro
import com.ucb.domain.simulacro.Finanza

interface IFinanzasLocalDataSource {
    suspend fun doEgreso(finanza:Finanza):Boolean
    suspend fun doIngreso(finanza:Finanza):Boolean
    suspend fun deleteRegistro(nombre:String):Boolean
    suspend fun getRegistros():List<Finanza>

}