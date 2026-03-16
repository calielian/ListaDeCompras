package com.calielian.listadecompras.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Classe de dados que age como as tabelas do banco de dados
    Cada atributo representa uma coluna da tabela
 */
@Entity(
    tableName = "Corredor"
)
data class CorredorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nome") val nome: String?
)
