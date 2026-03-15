package com.calielian.listadecompras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.calielian.listadecompras.databinding.FragmentCorredorBinding
import com.calielian.listadecompras.recyclercomponents.CorredorAdapter

class CorredorFragment : Fragment() {

    private var _binding: FragmentCorredorBinding? = null
    private val binding get() = _binding!! // o operador !! força _binding a não nulo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCorredorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as MainApp
        val dao = app.database.corredorDao()
        val adapter = CorredorAdapter()

        binding.listaCorredores.layoutManager = LinearLayoutManager(context)
        binding.listaCorredores.adapter = adapter

        dao.pegarTodos().asLiveData().observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}