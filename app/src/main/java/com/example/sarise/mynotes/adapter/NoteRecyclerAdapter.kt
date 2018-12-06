package com.example.sarise.mynotes.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sarise.mynotes.R
import com.example.sarise.mynotes.model.Note
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_lista_activity.view.*

class NoteRecyclerAdapter internal constructor(context: Context) :
RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>(){

    var onItemClick: ((Note) -> Unit)? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes  = emptyList<Note>() // Cached copy of friends
    //  private val mContext = context

    // infla o layout do item da lista para cada componente da lista
    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_lista_activity , holder,
            false )
        return ViewHolder(view)
    }

    // retorna o tamanho da lista
    override fun getItemCount() = notes.size

    // colocando os itens da lista nos itens de view da lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = notes[position]
        holder.tituloNota.text = current.titulo
    }

    // classe para mapear os componentes do item da lista
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tituloNota: TextView = itemView.txtNoteTitulo

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(notes[adapterPosition])
            }
        }

    }


    fun setNoteList(friendList: List<Note>){
        this.notes = friendList
        notifyDataSetChanged()
    }


}