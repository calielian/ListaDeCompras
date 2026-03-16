package com.calielian.listadecompras.recyclercomponents

import androidx.recyclerview.widget.RecyclerView
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.ProdutoBinding

class ProdutoViewHolder(private val binding: ProdutoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(produto: ProdutoEntity, onCheckChanged: (ProdutoEntity) -> Unit) {
        binding.nomeProduto.text = produto.nome
        binding.checkboxComprado.isChecked = produto.comprado
        binding.quantidade.setText(produto.quantidade.toString())

        binding.checkboxComprado.setOnCheckedChangeListener { _, isChecked ->
            val produtoAtualizado = produto.copy(comprado = isChecked)
            onCheckChanged(produtoAtualizado)
        }
    }
}