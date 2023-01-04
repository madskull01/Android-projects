package com.example.notesrealm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults

class NotesAdapter(private val context: Context,private val notesList:RealmResults<Notes>):RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.notes_rv_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=notesList[position]
        holder.titletv.text=currentItem!!.title
        holder.desctv.text= currentItem.description
        holder.idtv.text=currentItem.id.toString()
        holder.delete.setOnClickListener {
            random().deleteNote(currentItem.id)
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titletv=itemView.findViewById<TextView>(R.id.titleTV)
        val desctv=itemView.findViewById<TextView>(R.id.descTV)
        val idtv=itemView.findViewById<TextView>(R.id.idTV)
        val delete=itemView.findViewById<Button>(R.id.delete)
    }
}