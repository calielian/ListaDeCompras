package com.calielian.listadecompras

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.calielian.listadecompras.database.CorredorEntity
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.ActivityMainBinding
import com.calielian.listadecompras.viewmodels.CorredorViewModel
import com.calielian.listadecompras.viewmodels.CorredorViewModelFactory
import com.calielian.listadecompras.viewmodels.ProdutoViewModel
import com.calielian.listadecompras.viewmodels.ProdutoViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val corredorViewModel: CorredorViewModel by lazy {
        val app = application as MainApp
        val factory = CorredorViewModelFactory(app.database.corredorDao())
        ViewModelProvider(this, factory)[CorredorViewModel::class.java]
    }

    private val produtoViewModel: ProdutoViewModel by lazy {
        val app = application as MainApp
        val factory = ProdutoViewModelFactory(app.database.produtoDao())
        ViewModelProvider(this, factory)[ProdutoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoAdicionar.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

            if (fragment is CorredorFragment) {
                mostrarDialogoNovoCorredor()
            } else if (fragment is ProdutosFragment) {
                mostrarDialogoNovoProduto()
            }
        }
    }

    private fun mostrarDialogoNovoCorredor() {
        val input = EditText(this)

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.new_corridor))
            .setView(input)
            .setPositiveButton("Salvar") { _, _ ->
                val nome = input.text.toString()
                if (nome.isNotEmpty()) {
                    corredorViewModel.inserir(CorredorEntity(nome = nome))
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoNovoProduto() {
        val input = EditText(this)

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.new_product))
            .setView(input)
            .setPositiveButton("Salvar") { _, _ ->
                val nome = input.text.toString()
                if (nome.isNotEmpty()) {
                    produtoViewModel.inserir(ProdutoEntity(nome = nome, idCorredor = corredorId, quantidade = 1, comprado = false))
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    companion object {
        var corredorId: Int = 0
    }
}