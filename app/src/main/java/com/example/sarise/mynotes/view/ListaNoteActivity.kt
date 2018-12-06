package com.example.sarise.mynotes.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.sarise.mynotes.R
import com.example.sarise.mynotes.adapter.NoteRecyclerAdapter
import com.example.sarise.mynotes.model.Note
import com.example.sarise.mynotes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_lista_note.*

class ListaNoteActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    private val requestCodeNote = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_note)

        fabAddNote.setOnClickListener {
            val intent = Intent(this@ListaNoteActivity, NovaNoteActivity::class.java)
            // abre uma nova activity, mas espera um resultado que será validado com a chave
            // que estou enviand - requestCodeAddAmigo
            startActivityForResult(intent, requestCodeNote)
        }

        val recyclerView = rvListaNotes
        val adapter = NoteRecyclerAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.onItemClick = {
            val intent = Intent(this@ListaNoteActivity,
                NovaNoteActivity::class.java)
            intent.putExtra(NovaNoteActivity.EXTRA_REPLY, it)
            startActivityForResult(intent, requestCodeNote )
        }

        noteViewModel =
                ViewModelProviders.of(this).
                    get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this, Observer {notes->
            notes?.let { adapter.setNoteList(it) }

        })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == requestCodeNote &&
            resultCode == Activity.RESULT_OK) {
            data.let {
                try {
                    /** caso exista o objeto recebido, adicione-o em um objeto do tipo
                     * Friend. Para isso precisaremos pegar o dado serializado
                     * e feito cast de Friend para dizer que ele de fato é o objeto
                     * que pretendo receber
                     */
                    val note: Note = data?.getSerializableExtra(
                        NovaNoteActivity.EXTRA_REPLY) as Note

                    // se tiver id > 0, atualizo, caso contrário insiro
                    if (note.id > 0) noteViewModel.update(note)
                    else noteViewModel.insert(note)
                } catch (e: Exception){
                    val note: Note = data?.getSerializableExtra(
                        NovaNoteActivity.EXTRA_DELETE) as Note
                    noteViewModel.delete(note)
                }

            }
        }
    }
}


