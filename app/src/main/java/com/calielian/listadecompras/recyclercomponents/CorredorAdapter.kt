package com.calielian.listadecompras.recyclercomponents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.calielian.listadecompras.database.CorredorEntity
import com.calielian.listadecompras.databinding.CorredorBinding

/*
    Adapter é um componente do RecyclerView
    Ponte entre os dados e a interface
 */
class CorredorAdapter : ListAdapter<CorredorEntity, CorredorViewHolder>(DiffCallback()) {
    var onItemClick: ((CorredorEntity) -> Unit)? = null
    var onItemLongClick: ((CorredorEntity) -> Unit)? = null

    // infla o layout e retorna o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorredorViewHolder {
        val binding = CorredorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CorredorViewHolder(binding)
    }

    // entrega um item específico e entrega para o ViewHolder para preencher os dados e ser mostrado
    override fun onBindViewHolder(holder: CorredorViewHolder, posicao: Int) {
        val item = getItem(posicao)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(item)
            true
        }
    }

    // classe que verifica se itens são iguais ou não
    class DiffCallback : DiffUtil.ItemCallback<CorredorEntity>() {
        override fun areItemsTheSame(antigo: CorredorEntity, novo: CorredorEntity): Boolean {
            return antigo.id == novo.id
        }

        override fun areContentsTheSame(antigo: CorredorEntity, novo: CorredorEntity): Boolean {
            return antigo == novo
        }
    }
}