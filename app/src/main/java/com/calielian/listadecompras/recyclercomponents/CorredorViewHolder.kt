package com.calielian.listadecompras.recyclercomponents

import androidx.recyclerview.widget.RecyclerView
import com.calielian.listadecompras.database.CorredorEntity
import com.calielian.listadecompras.databinding.CorredorBinding

class CorredorViewHolder(private val binding: CorredorBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(corredor: CorredorEntity) {
        binding.textoNomeCorredor.text = corredor.nome
    }
}