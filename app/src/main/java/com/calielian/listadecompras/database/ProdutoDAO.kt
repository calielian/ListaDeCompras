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
interface ProdutoDAO {
    @Query("SELECT * FROM Produto WHERE id_corredor = :idCorredor")
    fun pegarTodosPorCorredor(idCorredor: Int): Flow<List<ProdutoEntity>>

    @Query("SELECT exists(SELECT 1 FROM Produto WHERE nome = :nome LIMIT 1)")
    suspend fun existeProduto(nome: String): Boolean

    @Insert
    suspend fun inserir(produto: ProdutoEntity)

    @Delete
    suspend fun deletar(produto: ProdutoEntity)

    @Query("DELETE FROM Produto")
    suspend fun deletarTodos()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Produto'")
    suspend fun deletarSequencia()

    @Query("UPDATE Produto SET comprado = :comprado WHERE id = :id")
    suspend fun atualizarComprado(id: Int, comprado: Boolean)

    @Query("UPDATE Produto SET nome = :nome WHERE id = :id")
    suspend fun atualizarNome(id: Int, nome: String)

    @Query("UPDATE Produto SET quantidade = :quantidade WHERE id = :id")
    suspend fun atualizarQuantidade(id: Int, quantidade: Int)
}
