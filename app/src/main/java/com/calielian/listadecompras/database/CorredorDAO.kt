package com.calielian.listadecompras.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CorredorDAO {
    @Query("SELECT * FROM Corredor")
    fun pegarTodos(): Flow<List<CorredorEntity>>

    @Insert
    suspend fun inserir(corredor: CorredorEntity)

    @Delete
    suspend fun deletar(corredor: CorredorEntity)
}