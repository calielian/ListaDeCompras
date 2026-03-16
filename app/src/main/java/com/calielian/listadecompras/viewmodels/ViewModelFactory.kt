package com.calielian.listadecompras.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calielian.listadecompras.database.CorredorDAO
import com.calielian.listadecompras.database.ProdutoDAO

/*
    ViewModelFactory personalizado tem a função de criar instâncias de ViewModel com parâmetros
    (neste caso, o DAO)

    usar "val vm = ProdutoViewModel(dao)" faz o Android recriar o ViewModel toda vez que a tela girar
    Utilizando o ViewModelProvider faz o sistema verificar se já possui um ViewModel para a tela
    Além de vincular ao ciclo de vida da Activity/Fragment
 */

class CorredorViewModelFactory(private val dao: CorredorDAO) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        // verifica se modelClass é uma instância de CorredorViewModel
        if (modelClass.isAssignableFrom(CorredorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CorredorViewModel(dao) as T // retorna o ViewModel como o tipo especificado "T" (tipo esperado do retorno)
            // é seguro o type casting para T porque foi verificado no if
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}

class ProdutoViewModelFactory(private val dao: ProdutoDAO) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProdutoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProdutoViewModel(dao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}