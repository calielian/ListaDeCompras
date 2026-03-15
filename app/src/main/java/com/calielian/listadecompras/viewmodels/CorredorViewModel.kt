package com.calielian.listadecompras.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.calielian.listadecompras.database.CorredorDAO
import com.calielian.listadecompras.database.CorredorEntity
import kotlinx.coroutines.launch

class CorredorViewModel(private val dao: CorredorDAO) : ViewModel() {
    val listaCorredores = dao.pegarTodos().asLiveData()

    fun inserir(corredor: CorredorEntity) {
        viewModelScope.launch {
            dao.inserir(corredor)
        }
    }

    fun deletar(corredor: CorredorEntity) {
        viewModelScope.launch {
            dao.deletar(corredor)
        }
    }
}