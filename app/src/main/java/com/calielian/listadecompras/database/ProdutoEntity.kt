package com.calielian.listadecompras.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Produto"
)
data class ProdutoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "id_corredor") val idCorredor: Int?,
    @ColumnInfo(name = "nome") val nome: String?,
    @ColumnInfo(name = "quantidade") val quantidade: Int?,
    @ColumnInfo(name = "comprado") val comprado: Boolean
)
