package com.calielian.listadecompras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.calielian.listadecompras.databinding.FragmentCorredorBinding
import com.calielian.listadecompras.recyclercomponents.CorredorAdapter
import com.calielian.listadecompras.viewmodels.CorredorViewModel
import com.calielian.listadecompras.viewmodels.CorredorViewModelFactory
import com.google.android.material.transition.MaterialSharedAxis

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCorredorBinding.inflate(inflater, container, false)
        return binding.root
    }

    // view visível e hierarquia criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as MainApp
        val factory = CorredorViewModelFactory(app.database.corredorDao())
        val viewModel = ViewModelProvider(this, factory)[CorredorViewModel::class.java]
        val adapter = CorredorAdapter()

        adapter.onItemClick = { corredor ->
            val fragment = ProdutosFragment.newInstance(corredor.id)
            MainActivity.corredorId = corredor.id
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
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