package com.calielian.listadecompras.recyclercomponents

import androidx.recyclerview.widget.RecyclerView
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.ProdutoBinding

/*
    ViewHolder é um componente do RecyclerView
    Objeto que guarda as referências para os componentes visuais de uma única linha da lista
    Sem ele, o Android procuraria cada componente pelo ID toda vez que fosse rolado a tela
 */
class ProdutoViewHolder(private val binding: ProdutoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(produto: ProdutoEntity, onCheckChanged: (ProdutoEntity) -> Unit, onValueChanged: (ProdutoEntity) -> Unit, onLongClick: (ProdutoEntity) -> Unit) {
        binding.nomeProduto.text = produto.nome
        binding.checkboxComprado.isChecked = produto.comprado
        binding.quantidade.setText(produto.quantidade.toString())

        binding.checkboxComprado.setOnCheckedChangeListener { _, isChecked ->
            val produtoAtualizado = produto.copy(comprado = isChecked)
            onCheckChanged(produtoAtualizado)
        }

        binding.quantidade.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val novaQuantidade = binding.quantidade.text.toString().toIntOrNull() ?: 0
                val produtoAtualizado = produto.copy(quantidade = novaQuantidade)
                onValueChanged(produtoAtualizado)
            }
        }

        binding.root.setOnLongClickListener {
            onLongClick(produto)
            true
        }
    }
}