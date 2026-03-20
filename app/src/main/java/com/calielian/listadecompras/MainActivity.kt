package com.calielian.listadecompras

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.calielian.listadecompras.database.CorredorEntity
import com.calielian.listadecompras.database.ProdutoEntity
import com.calielian.listadecompras.databinding.ActivityMainBinding
import com.calielian.listadecompras.databinding.AlertDialogCorredorNewBinding
import com.calielian.listadecompras.databinding.AlertDialogProdutoNewBinding
import com.calielian.listadecompras.databinding.LayoutMenuOpcoesBinding
import com.calielian.listadecompras.viewmodels.CorredorViewModel
import com.calielian.listadecompras.viewmodels.CorredorViewModelFactory
import com.calielian.listadecompras.viewmodels.ProdutoViewModel
import com.calielian.listadecompras.viewmodels.ProdutoViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

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

        binding.fab.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            val menuBinding = LayoutMenuOpcoesBinding.inflate(layoutInflater)
            bottomSheet.setContentView(menuBinding.root)

            menuBinding.navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.limpar -> {
                        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) // pega o fragmento atual

                        // verifica se o fragmento ProdutosFragment está na tela
                        if (fragment is ProdutosFragment) {
                            Snackbar.make(
                                this,
                                binding.root,
                                getString(R.string.exit_corridor_first),
                                Snackbar.LENGTH_LONG
                            ).show()
                            bottomSheet.dismiss()
                            return@setNavigationItemSelectedListener true
                        }

                        Snackbar.make(
                            this,
                            binding.root,
                            getString(R.string.irreversible_action),
                            Snackbar.LENGTH_LONG
                        ).setAction(getString(R.string.yes)) {
                            corredorViewModel.deletarTodos()
                            produtoViewModel.deletarTodos()
                        }.show()
                        bottomSheet.dismiss()
                        true
                    }

                    R.id.sobre -> {
                        supportFragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, SobreFragment())
                            .commit()
                        alternarVisibilidadeFAB()
                        bottomSheet.dismiss()
                        true
                    }

                    R.id.novo_item -> {
                        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) // pega o fragmento atual

                        // verifica qual fragmento está na tela
                        if (fragment is CorredorFragment) {
                            mostrarDialogoNovoCorredor()
                        } else if (fragment is ProdutosFragment) {
                            mostrarDialogoNovoProduto()
                        }
                        bottomSheet.dismiss()
                        true
                    }

                    else -> false
                }
            }

            bottomSheet.show()
        }
    }

    private fun mostrarDialogoNovoCorredor() {

        val dialogBinding = AlertDialogCorredorNewBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.salvar.setOnClickListener {
            lifecycleScope.launch {
                val nome = dialogBinding.corridorName.text.toString()

                if (nome.isNotEmpty() && !corredorViewModel.existeCorredor(nome)) {
                    corredorViewModel.inserir(CorredorEntity(nome = nome))
                    dialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, getString(R.string.corridor_error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialogBinding.cancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarDialogoNovoProduto() {
        val dialogBinding = AlertDialogProdutoNewBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.salvar.setOnClickListener {
            lifecycleScope.launch {
                val nome = dialogBinding.productName.text.toString()

                if (nome.isNotEmpty() && !produtoViewModel.existeProduto(nome)) {
                    produtoViewModel.inserir(ProdutoEntity(nome = nome, idCorredor = corredorId, quantidade = 1, comprado = false))
                    dialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, getString(R.string.product_error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialogBinding.cancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun alternarVisibilidadeFAB() {
        binding.fab.visibility = if (binding.fab.isVisible) View.INVISIBLE else View.VISIBLE
    }

    companion object { // gera membros que operam como estáticos (não existe static em Kotlin)
        var corredorId: Int = 0
    }
}