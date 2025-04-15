package com.ucb.framework.simulacroPersistence

import androidx.datastore.preferences.protobuf.Internal.BooleanList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.framework.persistence.GitAccount
@Dao
interface IFinanzasRegistroDAO {
    @Query("SELECT * FROM finanzas_registro")
    fun getRegistros(): List<FinanzasRegistro>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(finanzaRegistro: FinanzasRegistro)

    @Query("DELETE FROM finanzas_registro")
    suspend fun deleteAll()

    @Query("SELECT * FROM finanzas_registro WHERE nombre = :nombre LIMIT 1")
    suspend fun findByNombre(nombre: String): FinanzasRegistro

    @Query("DELETE FROM finanzas_registro WHERE nombre = :nombre")
    suspend fun deleteByNombre(nombre: String): Int

}
