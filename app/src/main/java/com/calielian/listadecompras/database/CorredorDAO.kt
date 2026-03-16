package com.calielian.listadecompras.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/*
    DAO: Data Access Object
    Interface que lida com as operações de banco de dados
    Cada entidade deve ter seu DAO
    Se os métodos envolvem operações (escrita, deletar, atualizar), devem ser suspend (coroutine)
    Se os métodos não envolvem operações, retorne Flow<List<Entity>> ou LiveData<List<Entity>>
        Dessa forma o banco de dados gera uma nova lista sempre que for atualizado

    Como usa SQLite, podemos usar seus recursos para outros tipos de funções
 */
@Dao
interface CorredorDAO {
    @Query("SELECT * FROM Corredor")
    fun pegarTodos(): Flow<List<CorredorEntity>>

    @Insert
    suspend fun inserir(corredor: CorredorEntity)

    @Delete
    suspend fun deletar(corredor: CorredorEntity)
}