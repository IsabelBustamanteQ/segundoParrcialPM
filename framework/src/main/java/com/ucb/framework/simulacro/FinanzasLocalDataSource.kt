package com.ucb.framework.simulacro

import android.content.Context
import com.ucb.data.git.ILocalDataSource
import com.ucb.data.simulacro.IFinanzasLocalDataSource
import com.ucb.domain.Gitalias
import com.ucb.domain.simulacro.Finanza
import com.ucb.framework.mappers.toEntity
import com.ucb.framework.mappers.toModel
import com.ucb.framework.persistence.AppRoomDatabase
import com.ucb.framework.persistence.IGitAccountDAO
import com.ucb.framework.simulacroPersistence.FinanzasRegistro
import com.ucb.framework.simulacroPersistence.FinanzasRoomDatabase
import com.ucb.framework.simulacroPersistence.IFinanzasRegistroDAO

class FinanzasLocalDataSource(val context: Context) : IFinanzasLocalDataSource{
    val finanzasDAO: IFinanzasRegistroDAO = FinanzasRoomDatabase.getDatabase(context).finanzasDao()
    override suspend fun doIngreso(finanza:Finanza): Boolean {
        finanzasDAO.insert(finanza.toEntity())
        return true
    }
    override suspend fun doEgreso(finanza:Finanza): Boolean {
        finanzasDAO.insert(finanza.toEntity())
        return true
    }

    override suspend fun deleteRegistro(nombre: String): Boolean {
        finanzasDAO.deleteByNombre(nombre)
        return true
    }

    override suspend fun getRegistros(): List<Finanza> {
        return finanzasDAO.getRegistros().map { it.toModel() }
    }

}