package com.calielian.listadecompras.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDAO {
    @Query("SELECT * FROM Produto")
    fun pegarTodos(): Flow<List<ProdutoEntity>>

    @Query("SELECT * FROM Produto WHERE id_corredor = :idCorredor")
    fun pegarTodosPorCorredor(idCorredor: Int): Flow<List<ProdutoEntity>>

    @Query("SELECT exists(SELECT 1 FROM Produto WHERE nome = :nome)")
    suspend fun existeProduto(nome: String): Boolean

    @Insert
    suspend fun inserir(produto: ProdutoEntity)

    @Delete
    suspend fun deletar(produto: ProdutoEntity)

    @Query("UPDATE Produto SET marcado = :marcado WHERE id = :id")
    suspend fun atualizarMarcado(id: Int, marcado: Boolean)

    @Query("UPDATE Produto SET nome = :nome WHERE id = :id")
    suspend fun atualizarNome(id: Int, nome: String)

}
