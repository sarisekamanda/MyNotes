package com.example.sarise.mynotes.repository

import android.arch.lifecycle.LiveData
import com.example.sarise.mynotes.db.Note
import com.example.sarise.mynotes.db.NoteDao

class NoteRepository (private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAll()

    fun insert(note: Note){
        noteDao.insert(note)
    }
}