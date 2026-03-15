package com.calielian.listadecompras.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProdutoEntity::class, CorredorEntity::class],
    version = 1
)
abstract class ListaDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDAO
    abstract fun corredorDao(): CorredorDAO

    companion object {
        @Volatile
        private var INSTANCE: ListaDatabase? = null

        fun pegarDatabase(context: Context): ListaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ListaDatabase::class.java,
                    "lista_comptas_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}