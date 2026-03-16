package com.calielian.listadecompras.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
    Classe abstrata que representa o banco de dados como um tod0
    Deverá ter a lista de entidades e a versão do banco de dados
    Cada métod0 é abstrato e deve retornar o DAO de cada entidade
 */
@Database(
    entities = [ProdutoEntity::class, CorredorEntity::class],
    version = 1
)
abstract class ListaDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDAO
    abstract fun corredorDao(): CorredorDAO

    companion object { // gera membros que operam como estáticos (não existe static em Kotlin)
        @Volatile // faz com que a variável seja visível em todas as threads
        private var INSTANCE: ListaDatabase? = null
        // sem @Volatile, se a Thread A criar o banco de dados e salvar em INSTANCE,
        // Thread B ainda a vê como null porque está olhando na sua própria versão em cache

        fun pegarDatabase(context: Context): ListaDatabase {
            // o operador ?: (Elvis) verifica se INSTANCE é null
            // se não for null: retorna imediatamente
            // se for null: executa o bloco à direita
            // equivalente: return (INSTANCE != null) ? INSTANCE : synchronized(this) { ... }
            return INSTANCE ?: synchronized(this) {
                // synchronized(this) faz o bloco ser executado somente em uma Thread por vez
                // caso duas Threads tente criar o banco de dados, uma executará o bloco e outra irá esperar
                val instance = Room.databaseBuilder(
                    context.applicationContext, // recebe o contexto do aplicativo e não da activity para sobreviver caso as telas forem fechadas
                    ListaDatabase::class.java, // a classe que define o banco de dados
                    "lista_comptas_db" // nome do arquivo .db no celular
                ).build() // cria a instância
                INSTANCE = instance
                // salva a instância nova na variável global INSTANCE, para que o operador Elvis possa retornar a instância já criada
                instance // a última linha do bloco é o retorno do bloco
            }
        }
    }
}