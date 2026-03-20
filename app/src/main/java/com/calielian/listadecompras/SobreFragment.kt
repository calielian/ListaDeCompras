package com.calielian.listadecompras

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calielian.listadecompras.databinding.FragmentSobreBinding
import com.google.android.material.transition.MaterialSharedAxis

class SobreFragment : Fragment() {

    private var _binding: FragmentSobreBinding? = null
    private val binding get() = _binding!! // o operador !! força _binding a não nulo
    // é como se apelidasse um getter do _binding de "binding"
    // além de remover a necessidade do uso de "!!" ou "?" toda vez que fosse acessar o _binding

    // criação inicial do fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    // view não visível, nem hierarquia criada
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSobreBinding.inflate(inflater, container, false)
        return binding.root
    }

    // view visível e hierarquia criada
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.versionApp.text = "v${BuildConfig.VERSION_NAME}"
    }

    // fragment desacoplado da view onde estava
    override fun onDestroyView() {
        (activity as MainActivity).alternarVisibilidadeFAB()
        super.onDestroyView()
        _binding = null
    }
}