package com.calielian.listadecompras.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.calielian.listadecompras.database.ProdutoDAO
import com.calielian.listadecompras.database.ProdutoEntity
import kotlinx.coroutines.launch

// o ViewModel realiza as operações do DAO de forma correta
class ProdutoViewModel(private val dao: ProdutoDAO) : ViewModel() {
    fun pegarTodosPorCorredor(idCorredor: Int): LiveData<List<ProdutoEntity>> {
        return dao.pegarTodosPorCorredor(idCorredor).asLiveData()
    }

    suspend fun existeProduto(nome: String): Boolean {
        return dao.existeProduto(nome)
    }

    fun inserir(produto: ProdutoEntity) {
        viewModelScope.launch {
            dao.inserir(produto)
        }
    }

    fun deletar(produto: ProdutoEntity) {
        viewModelScope.launch {
            dao.deletar(produto)
        }
    }

    fun atualizarComprado(id: Int, comprado: Boolean) {
        viewModelScope.launch {
            dao.atualizarComprado(id, comprado)
        }
    }

    fun atualizarNome(id: Int, nome: String) {
        viewModelScope.launch {
            dao.atualizarNome(id, nome)
        }
    }

    fun atualizarQuantidade(id: Int, quantidade: Int) {
        viewModelScope.launch {
            dao.atualizarQuantidade(id, quantidade)
        }
    }

    fun deletarTodos() {
        viewModelScope.launch {
            dao.deletarTodos()
            dao.deletarSequencia()
        }
    }

}