package com.example.sarise.mynotes.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {


    abstract fun noteDao():NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope):NoteDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note-database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(FriendDatabaseCalback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class NoteDatabaseCalback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populaTabela(database.noteDao())
                }
            }
        }

        fun populaTabela(noteDao: NoteDao){
         }
}

}