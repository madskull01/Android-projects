package com.example.testrealm


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class NotesAdapter(private val context: Context,private val notesList: OrderedRealmCollection<Notes>,private val user: User,private val partition: String):RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.notes_rv_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=notesList[position]
        holder.titletv.text=currentItem!!.title
        holder.desctv.text= currentItem.description
        holder.delete.setOnClickListener {
            val dialog= AlertDialog.Builder(context)
            dialog.setTitle("confirm")
            dialog.setMessage("are you sure you want to delete it")
            dialog.setPositiveButton("Delete"){text,listener->
                removeAt(currentItem.id)
            }
            dialog.setNegativeButton("Close"){text,listener->

            }
            dialog.create()
            dialog.show()

            Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show()
        }
        holder.cardNotes.setOnClickListener {
            val intent=Intent(context,UpdateActivity::class.java)
            intent.putExtra("key",currentItem.id)
            intent.putExtra("title",currentItem.title)
            intent.putExtra("desc",currentItem.description)
            context.startActivity(intent)
            Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show()
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    private fun removeAt(id: String) {
        // need to create a separate instance of realm to issue an update, since this event is
        // handled by a background thread and realm instances cannot be shared across threads
        val config = SyncConfiguration.Builder(user, partition)
            .build()

        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        val realm: Realm = Realm.getInstance(config)
        // execute Transaction asynchronously to avoid blocking the UI thread
        realm.executeTransactionAsync {
            // using our thread-local new realm instance, query for and delete the task
            val item = it.where<Notes>().equalTo("id", id).findFirst()
            item?.deleteFromRealm()
        }
        // always close realms when you are done with them!
        realm.close()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titletv=itemView.findViewById<TextView>(R.id.titleTV)
        val desctv: TextView =itemView.findViewById(R.id.descTV)
        val delete=itemView.findViewById<Button>(R.id.delete)
        val idtv=itemView.findViewById<TextView>(R.id.idTV)
        val cardNotes=itemView.findViewById<CardView>(R.id.cardNotes)
    }
}