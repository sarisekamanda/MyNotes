package com.example.sarise.mynotes.db

//import com.example.sarise.mynotes.model.Note
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.sarise.mynotes.model.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update (note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Query("SELECT * from note_table ORDER BY titulo ASC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * from note_table WHERE id = :id")
    fun getNote (id: Long): LiveData<Note>

    @Query("SELECT * FROM note_table")
    fun allNotes():LiveData<List<Note>>

}