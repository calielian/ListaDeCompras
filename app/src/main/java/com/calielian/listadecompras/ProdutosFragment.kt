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
import com.calielian.listadecompras.databinding.AlertDialogProdutoOperationBinding
import com.calielian.listadecompras.databinding.FragmentProdutosBinding
import com.calielian.listadecompras.recyclercomponents.ProdutoAdapter
import com.calielian.listadecompras.viewmodels.ProdutoViewModel
import com.calielian.listadecompras.viewmodels.ProdutoViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

/*
    Fragment é como se fosse uma Activity, mas como um fragmento
 */
class ProdutosFragment : Fragment() {

    private var _binding: FragmentProdutosBinding? = null
    private val binding get() = _binding!! // o operador !! força _binding a não nulo
    // é como se apelidasse um getter do _binding de "binding"
    // além de remover a necessidade do uso de "!!" ou "?" toda vez que fosse acessar o _binding

    private var corredorId: Int = -1

    // criação inicial do fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        arguments?.let {
            corredorId = it.getInt(ARG_CORREDOR_ID)
        }
    }

    // view não visível, nem hierarquia criada
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProdutosBinding.inflate(inflater, container, false)
        return binding.root
    }

    // view visível e hierarquia criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as MainApp

        val factory = ProdutoViewModelFactory(app.database.produtoDao())
        val viewModel = ViewModelProvider(this, factory)[ProdutoViewModel::class.java]

        val adapter = ProdutoAdapter().apply {
            this.onCheckChange = { produto ->
                viewModel.atualizarComprado(produto.id, produto.comprado)
            }

            this.onValueChange = { produto ->
                viewModel.atualizarQuantidade(produto.id, produto.quantidade!!)
            }

            this.onLongClick = { produto ->
                val dialogBinding = AlertDialogProdutoOperationBinding.inflate(layoutInflater)

                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogBinding.root)
                    .create()

                dialogBinding.productName.setText(produto.nome)

                dialogBinding.salvar.setOnClickListener {
                    lifecycleScope.launch {
                        val nome = dialogBinding.productName.text.toString()

                        if (nome.isNotEmpty() && nome != produto.nome && !viewModel.existeProduto(nome)) {
                            viewModel.atualizarNome(produto.id, nome)
                            dialog.dismiss()
                        } else {
                            Toast.makeText(context, "Produto já existe/nome vazio", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                dialogBinding.delete.setOnClickListener {
                    viewModel.deletar(produto)
                    dialog.dismiss()
                }

                dialogBinding.cancelar.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }

        binding.listaProdutos.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.pegarTodosPorCorredor(corredorId).observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }
    }

    // fragment desacoplado da view onde estava
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object { // gera membros que operam como estáticos (não existe static em Kotlin)
        private const val ARG_CORREDOR_ID = "corredorId"

        // gera um métod0 estático adicional para interoperabilidade com Java
        //  (ao invés de ProdutosFragment.companion.newInstance, seria ProdutosFragment.newInstance())
        @JvmStatic
        fun newInstance(id: Int) = ProdutosFragment().apply { // .apply configura a classe e retorna a instância
            arguments = Bundle().apply {
                putInt(ARG_CORREDOR_ID, id)
            }
        }

        /*
            O código acima é equivalente a:
            fun newInstance(id: Int): ProdutosFragment {
                val fragment = ProdutosFragment()

                val args = Bundle()
                args.putInt(ARG_CORREDOR_ID, id)
                fragment.arguments = args

                return fragment
            }
         */
    }
}