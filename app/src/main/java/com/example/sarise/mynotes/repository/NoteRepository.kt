package com.example.sarise.mynotes.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.sarise.mynotes.model.Note
import com.example.sarise.mynotes.db.NoteDao

class NoteRepository (private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    fun delete(note: Note) {
        noteDao.delete(note)
    }

    fun update(note: Note) {
        noteDao.update(note)
    }

}