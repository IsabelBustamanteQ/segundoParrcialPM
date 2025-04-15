package com.ucb.data

import com.ucb.data.git.ILocalDataSource
import com.ucb.data.simulacro.IFinanzasLocalDataSource
import com.ucb.domain.Gitalias
import com.ucb.domain.simulacro.Finanza

class FinanzasRepository(private val localFinanzasDataSource: IFinanzasLocalDataSource) {
    suspend fun doIngreso(finanza:Finanza): Boolean {
        this.localFinanzasDataSource.doIngreso(finanza);
        return true
    }
    suspend fun doEgreso(finanza:Finanza): Boolean {
        this.localFinanzasDataSource.doEgreso(finanza);
        return true
    }
    suspend fun deleteRegistro(nombre:String): Boolean {
        this.localFinanzasDataSource.deleteRegistro(nombre);
        return true
    }
    suspend fun getRegistros():List<Finanza>{
        return this.localFinanzasDataSource.getRegistros();
    }
}