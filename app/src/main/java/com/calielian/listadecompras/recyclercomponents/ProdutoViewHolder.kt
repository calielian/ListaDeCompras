package com.calielian.listadecompras.recyclercomponents

import android.graphics.Paint
import android.graphics.Typeface
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
        atualizarEstiloTexto(produto.comprado)
        binding.quantidade.setText(produto.quantidade.toString())

        binding.checkboxComprado.setOnCheckedChangeListener { _, isChecked ->
            atualizarEstiloTexto(isChecked)
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

    private fun atualizarEstiloTexto(comprado: Boolean) {
        if (comprado) {
            binding.nomeProduto.paintFlags = binding.nomeProduto.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.nomeProduto.setTypeface(null, Typeface.ITALIC)
            binding.nomeProduto.alpha = 0.5f

            /*
                o "or" aqui é uma operação bitwise
                paintFlags é um número inteiro que representa uma linha de bits

                o OR lógico foi usado porque ele ADICIONA: 1000 OR 0001 = 1001
                ("0 OR 1" retorna 1, por isso ele ADICIONA,
                onde tem "0 OR 0" continua 0 e onde tiver "0 or 1" ou "1 or 1" fica/continua 1)
             */
        } else {
            binding.nomeProduto.paintFlags = binding.nomeProduto.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.nomeProduto.setTypeface(null, Typeface.NORMAL)
            binding.nomeProduto.alpha = 1.0f

            /*
                o "and" aqui é uma operação bitwise
                AND lógico retorna 1 quando ambos forem 1
                o métod0 inv() inverte os bits

                inv() foi usado porque ao inverter os bits, quando for realizar a operação AND será removido
                os bits invertidos (pois se for 1 vai ser 0, e onde esse 0 estiver vai estar removido)

                Ex.: se os bits para ativar o estilo X do texto for 1000, ao executar inv() ficará 0111
                Ao pegar o paintFlags e comparar com AND com os bits invertidos, será removido o estilo X. Usando paintFlags = 1001:
                1001 AND 0111 = 0001 (o estilo X (1000) foi removido, permanecendo somente o estilo original (0001))
             */
        }
    }
}