package com.example.testrealm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class MainActivity : AppCompatActivity() {
    private lateinit var addNotes: FloatingActionButton
    private lateinit var notesRV: RecyclerView
    private lateinit var realm: Realm
    private lateinit var adapter: NotesAdapter

    private var user: User? = null

    override fun onStart() {
        super.onStart()
        user = taskApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else {
            val config = SyncConfiguration.Builder(user!!, user?.id)
                .build()
            Log.d("msg","log1")
            Realm.getInstanceAsync(config, object : Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    Log.d("msg","log2")
                    // since this realm should live exactly as long as this activity, assign the realm to a member variable
                    this@MainActivity.realm = realm
                    Log.d("msg","log3")
                    setUpRecyclerView(realm)
                    Log.d("msg","log4")

                }
            })
        }
    }
    override fun onStop() {
        super.onStop()
        user.run {
            realm.close()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addNotes=findViewById(R.id.addNotes)
        notesRV=findViewById(R.id.notesRV)
        addNotes.setOnClickListener {
            startActivity(Intent(this,AddNotesActivity::class.java))

        }
    }
    private fun setUpRecyclerView(realm: Realm) {
        adapter = NotesAdapter(this,realm.where<Notes>().sort("id").findAll(),user!!,user!!.id)
        notesRV.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        notesRV.adapter = adapter
        notesRV.setHasFixedSize(true)
        notesRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
    override fun onDestroy() {
        super.onDestroy()
        // its recommended to close realm references even if the user does not logout
        realm.close()
    }
}