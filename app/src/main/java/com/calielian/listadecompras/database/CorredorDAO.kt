package com.calielian.listadecompras.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CorredorDAO {
    @Query("SELECT * FROM Corredor")
    fun pegarTodos(): Flow<List<CorredorEntity>>

    @Query("SELECT * FROM Corredor WHERE id = :id")
    fun pegarPorId(id: Int): Flow<List<CorredorEntity>>

    @Delete
    suspend fun deletar(corredor: CorredorEntity)
}