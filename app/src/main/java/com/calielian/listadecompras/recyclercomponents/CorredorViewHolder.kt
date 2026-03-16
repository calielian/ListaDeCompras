package com.calielian.listadecompras.recyclercomponents

import androidx.recyclerview.widget.RecyclerView
import com.calielian.listadecompras.database.CorredorEntity
import com.calielian.listadecompras.databinding.CorredorBinding

/*
    ViewHolder é um componente do RecyclerView
    Objeto que guarda as referências para os componentes visuais de uma única linha da lista
    Sem ele, o Android procuraria cada componente pelo ID toda vez que fosse rolado a tela
 */
class CorredorViewHolder(private val binding: CorredorBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(corredor: CorredorEntity) {
        binding.textoNomeCorredor.text = corredor.nome
    }
}