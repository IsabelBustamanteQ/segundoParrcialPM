package com.ucb.framework.simulacroPersistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FinanzasRegistro::class], version = 1, exportSchema = false)
abstract class FinanzasRoomDatabase : RoomDatabase() {
    abstract fun finanzasDao(): IFinanzasRegistroDAO

    companion object {
        @Volatile
        var Instance: FinanzasRoomDatabase? = null

        fun getDatabase(context: Context): FinanzasRoomDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, FinanzasRoomDatabase::class.java, "finanzas_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

