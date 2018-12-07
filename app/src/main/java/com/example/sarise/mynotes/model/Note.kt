package com.example.sarise.mynotes.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "note_table")
data class Note(

    @ColumnInfo(name = "titulo")
    @NotNull
    var titulo: String,

    @ColumnInfo(name = "conteudo")
    var conteudo: String = "",

    @ColumnInfo(name = "notePhoto")
    var notePhoto: String = ""

        ): Serializable {
         @PrimaryKey(autoGenerate = true)
         @ColumnInfo(name = "id")
          var id: Long = 0}