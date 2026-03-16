package com.calielian.listadecompras

import android.os.Bundle
import android.widget.EditText
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

/*
    Classe da Activity "activity_main.xml" (conforme definido na própria activity)
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // variável privada mutável do tipo ActivityMainBinding
    // lateinit indica que a variável vai ser inicializada depois e antes do uso
    // (Kotlin não permite que variáveis não sejam inicializadas)
    // (é uma forma de inicializar sem precisar de definir que aceita nulo (ActivityMainBinding?))

    // "by" delega a inicialização para outra coisa
    // "lazy" indica que só é inicializada quando for usada pela primeira vez
    private val corredorViewModel: CorredorViewModel by lazy {
        val app = application as MainApp // type casting para MainApp
        val factory = CorredorViewModelFactory(app.database.corredorDao())
        ViewModelProvider(this, factory)[CorredorViewModel::class.java]
    }

    private val produtoViewModel: ProdutoViewModel by lazy {
        val app = application as MainApp
        val factory = ProdutoViewModelFactory(app.database.produtoDao())
        ViewModelProvider(this, factory)[ProdutoViewModel::class.java]
    }

    // criação inicial da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, CorredorFragment())
                .commit()
        }

        binding.botaoAdicionar.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) // pega o fragmento

            // verifica qual fragmento está na tela
            if (fragment is CorredorFragment) {
                mostrarDialogoNovoCorredor()
            } else if (fragment is ProdutosFragment) {
                mostrarDialogoNovoProduto()
            }
        }
    }

    private fun mostrarDialogoNovoCorredor() {
        val input = EditText(this)

        // Cria um diálogo de alerta
        MaterialAlertDialogBuilder(this) // contexto que será aberto
            .setTitle(getString(R.string.new_corridor)) // título do diálogo
            .setView(input) // adiciona o EditText ao diálogo
            .setPositiveButton("Salvar") { _, _ -> // define o texto do botão de confirmação (positivo) e sua ação
                val nome = input.text.toString()
                if (nome.isNotEmpty()) {
                    corredorViewModel.inserir(CorredorEntity(nome = nome))
                }
            }
            .setNegativeButton("Cancelar", null) // define o texto do botão de negação (negativo) e sua ação
            .show() // mostra o diálogo
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

    companion object { // gera membros que operam como estáticos (não existe static em Kotlin)
        var corredorId: Int = 0
    }
}