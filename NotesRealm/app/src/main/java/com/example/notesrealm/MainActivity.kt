package com.example.notesrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    private lateinit var addNotes:FloatingActionButton
    private lateinit var notesRV:RecyclerView
    private lateinit var notesList:ArrayList<Notes>
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init views
        Log.d("msg","error1")
        realm= Realm.getDefaultInstance()
        Log.d("msg","error2")

        addNotes=findViewById(R.id.addNotes)
        notesRV=findViewById(R.id.notesRV)

        //onclick add notes btn

        addNotes.setOnClickListener {
            startActivity(Intent(this,AddNotesActivity::class.java))

        }
        notesRV.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        getAllNotes()
    }

    private fun getAllNotes() {
        notesList= ArrayList()

        val results:RealmResults<Notes> = realm.where(Notes::class.java).findAll()

        notesRV.adapter=NotesAdapter(this,results)

        notesRV.adapter?.notifyDataSetChanged()
    }
}