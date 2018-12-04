package com.example.sarise.mynotes.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.sarise.mynotes.model.Note
import com.example.sarise.mynotes.db.NoteDatabase
import com.example.sarise.mynotes.repository.NoteRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class NoteViewModel(application: Application) : AndroidViewModel(application){

        private val repository: NoteRepository
        val allNotes: LiveData<List<Note>>

        private var parentJob = Job()

        private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

        private val scope = CoroutineScope(coroutineContext)

        init {
            val noteDao = NoteDatabase.getDatabase(application, scope).noteDAO()

            repository = NoteRepository(noteDao)
            allNotes = repository.allNotes
        }

        fun insert(note: Note) = scope.launch(Dispatchers.IO){
            repository.insert(note)
        }

        override fun onCleared() {
            super.onCleared()
            parentJob.cancel()
        }
}