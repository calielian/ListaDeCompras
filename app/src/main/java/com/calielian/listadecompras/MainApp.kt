package com.calielian.listadecompras

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.PredictiveBackControl
import com.calielian.listadecompras.database.ListaDatabase
import com.google.android.material.color.DynamicColors

/*
    Classe do aplicativo como um tod0 (como definido no AndroidManifest.xml)
 */
class MainApp : Application() {

    val database: ListaDatabase by lazy { ListaDatabase.pegarDatabase(this) }

    // executado quando o aplicativo é iniciado
    // antes de todas as activities
    @OptIn(PredictiveBackControl::class)
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this) // aplica o tema dinâmico do Material You
        FragmentManager.enablePredictiveBack(true) // ativa a animação de voltar preditivo do Material You (requer o opt-in)
    }
}