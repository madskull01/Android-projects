package com.example.testrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmModel
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class UpdateActivity : AppCompatActivity() {
    private lateinit var titleED: EditText
    private lateinit var descriptionED: EditText
    private lateinit var saveNotesBtn: Button
    private lateinit var realm: Realm
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
                    this@UpdateActivity.realm = realm
                    Log.d("msg","log3")
                    Log.d("msg","log4")

                }
            })
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        titleED=findViewById(R.id.title_EditText)
        descriptionED=findViewById(R.id.description_EditText)
        saveNotesBtn=findViewById(R.id.saveNotesBtn)


        val id=intent.getStringExtra("key")
        val title=intent.getStringExtra("title")
        val desc=intent.getStringExtra("desc")

        titleED.setText(title)
        descriptionED.setText(desc)
        saveNotesBtn.setOnClickListener {
            updateNotesToDatabase(id)
        }


    }
    private fun updateNotesToDatabase(id:String?){


        //copy this transaction and commit
        realm.executeTransactionAsync { realm ->
            val item = realm.where<Notes>().equalTo("id", id).findFirst()

            item?.title=titleED.text.toString()
            item?.description=descriptionED.text.toString()
            realm.insert( item as RealmModel)
        }

        Toast.makeText(this,"notes added succesfully", Toast.LENGTH_LONG).show()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        // its recommended to close realm references even if the user does not logout
        realm.close()
    }
}