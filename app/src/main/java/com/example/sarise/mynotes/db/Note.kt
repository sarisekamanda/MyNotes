package com.example.sarise.mynotes.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
data class Note(

    @ColumnInfo(name = "nome")
    var titulo: String,
    @ColumnInfo(name = "conteudo")
    var conteudo: String
        ): Serializable {
         @PrimaryKey(autoGenerate = true)
         @ColumnInfo(name = "id")
          val id: Long = 0}