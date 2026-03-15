package com.calielian.listadecompras.recyclercomponents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.calielian.listadecompras.database.CorredorEntity
import com.calielian.listadecompras.databinding.CorredorBinding

class CorredorAdapter : ListAdapter<CorredorEntity, CorredorViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorredorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CorredorBinding.inflate(layoutInflater, parent, false)
        return CorredorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CorredorViewHolder, posicao: Int) {
        holder.bind(getItem(posicao))
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