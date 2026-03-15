package com.calielian.listadecompras.recyclercomponents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.ProdutoBinding

class ProdutoAdapter : ListAdapter<ProdutoEntity, ProdutoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val binding = ProdutoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProdutoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, posicao: Int) {
        holder.bind(getItem(posicao))
    }

    // classe que verifica se itens são iguais ou não
    class DiffCallback : DiffUtil.ItemCallback<ProdutoEntity>() {
        override fun areItemsTheSame(antigo: ProdutoEntity, novo: ProdutoEntity) =
            antigo.id == novo.id

        override fun areContentsTheSame(antigo: ProdutoEntity, novo: ProdutoEntity) =
            antigo == novo
    }
}