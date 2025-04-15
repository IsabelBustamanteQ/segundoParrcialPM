package com.ucb.framework.simulacroPersistence

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity( tableName = "finanzas_registro")
data class FinanzasRegistro(
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "precio")
    var precio: Double,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "fecha")
    var fecha: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @ColumnInfo(name = "create_at")
    var createAt: String = LocalDateTime.now().toString()
}
