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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoNote: CircleImageView = itemView.imgNoteIcon
        val note: TextView = itemView.txtNoteTitulo

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(notes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_lista_activity, holder, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = notes[position]
        holder.note.text = current.titulo
        // it.photoFriend.text = friend.fSrcPhoto

        /* holder.itemView.setOnClickListener {v->
             val intent = Intent(v.context, NewFriendActivity::class.java)
             intent.putExtra("goFriend", current)
             v.context.startActivity(intent)
         }
         */

    }

    override fun getItemCount() = notes.size

    fun setNoteList(noteList: List<Note>){
        this.notes = noteList
        notifyDataSetChanged()
    }

}