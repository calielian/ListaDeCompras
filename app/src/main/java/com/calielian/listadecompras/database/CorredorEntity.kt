package com.calielian.listadecompras.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Corredor"
)
data class CorredorEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "nome") val nome: String?
)
