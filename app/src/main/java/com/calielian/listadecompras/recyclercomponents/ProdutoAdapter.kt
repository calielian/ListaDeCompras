package com.calielian.listadecompras.recyclercomponents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.ProdutoBinding

/*
    Adapter é um componente do RecyclerView
    Ponte entre os dados e a interface
 */
class ProdutoAdapter() : ListAdapter<ProdutoEntity, ProdutoViewHolder>(DiffCallback()) {

    var onValueChange: ((ProdutoEntity) -> Unit)? = null
    var onCheckChange: ((ProdutoEntity) -> Unit)? = null
    var onLongClick: ((ProdutoEntity) -> Unit)? = null


    // infla o layout e retorna o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val binding = ProdutoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProdutoViewHolder(binding)
    }

    // entrega um item específico e entrega para o ViewHolder para preencher os dados e ser mostrado
    override fun onBindViewHolder(holder: ProdutoViewHolder, posicao: Int) {
        holder.bind(getItem(posicao), onCheckChange!!, onValueChange!!, onLongClick!!)
    }

    // classe que verifica se itens são iguais ou não
    class DiffCallback : DiffUtil.ItemCallback<ProdutoEntity>() {
        override fun areItemsTheSame(antigo: ProdutoEntity, novo: ProdutoEntity) =
            antigo.id == novo.id

        override fun areContentsTheSame(antigo: ProdutoEntity, novo: ProdutoEntity) =
            antigo == novo
    }
}