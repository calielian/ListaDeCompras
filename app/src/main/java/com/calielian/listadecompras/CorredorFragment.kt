package com.calielian.listadecompras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.calielian.listadecompras.MainActivity.Companion.corredorId
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.AlertDialogCorredorOperationBinding
import com.calielian.listadecompras.databinding.AlertDialogProdutoNewBinding
import com.calielian.listadecompras.databinding.FragmentCorredorBinding
import com.calielian.listadecompras.recyclercomponents.CorredorAdapter
import com.calielian.listadecompras.viewmodels.CorredorViewModel
import com.calielian.listadecompras.viewmodels.CorredorViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

/*
    Fragment é como se fosse uma Activity, mas como um fragmento
 */
class CorredorFragment : Fragment() {

    private var _binding: FragmentCorredorBinding? = null
    private val binding get() = _binding!! // o operador !! força _binding a não nulo
    // é como se apelidasse um getter do _binding de "binding"
    // além de remover a necessidade do uso de "!!" ou "?" toda vez que fosse acessar o _binding

    // criação inicial do fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    // view não visível, nem hierarquia criada
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCorredorBinding.inflate(inflater, container, false)
        return binding.root
    }

    // view visível e hierarquia criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as MainApp
        val factory = CorredorViewModelFactory(app.database.corredorDao())
        val viewModel = ViewModelProvider(this, factory)[CorredorViewModel::class.java]
        val adapter = CorredorAdapter().apply {
            this.onItemClick = { corredor ->
                val fragment = ProdutosFragment.newInstance(corredor.id)
                MainActivity.corredorId = corredor.id
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            this.onItemLongClick = { corredor ->
                val dialogBinding = AlertDialogCorredorOperationBinding.inflate(layoutInflater)

                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogBinding.root)
                    .create()

                dialogBinding.corridorNameOp.setText(corredor.nome)

                dialogBinding.salvar.setOnClickListener {
                    lifecycleScope.launch {
                        val nome = dialogBinding.corridorNameOp.text.toString()

                        if (nome.isNotEmpty() && nome != corredor.nome && !viewModel.existeCorredor(nome)) {
                            viewModel.atualizarNome(corredor.id, nome)
                            dialog.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Corredor já existe/nome vazio", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                dialogBinding.delete.setOnClickListener {
                    viewModel.deletar(corredor)
                    dialog.dismiss()
                }

                dialogBinding.cancelar.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }

        binding.listaCorredores.layoutManager = LinearLayoutManager(context)
        binding.listaCorredores.adapter = adapter

        viewModel.listaCorredores.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }
    }

    // fragment desacoplado da view onde estava
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}