package com.example.notesrealm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

class AddNotesActivity : AppCompatActivity() {
    private lateinit var titleED:EditText
    private lateinit var descriptionED:EditText
    private lateinit var saveNotesBtn:Button
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        //init views
        Log.d("msg","helo1")

        titleED=findViewById(R.id.title_EditText)
        descriptionED=findViewById(R.id.description_EditText)
        saveNotesBtn=findViewById(R.id.saveNotesBtn)



        realm= Realm.getDefaultInstance()
        Log.d("msg","helo2")
        //onclick Listner

        saveNotesBtn.setOnClickListener {
            addNotesToDB()
        }
    }

    private fun addNotesToDB() {
        try {
            //Auto Increment ID
            realm.beginTransaction()
            val currentIdNumber:Number?=realm.where(Notes::class.java).max("id")
            val nextID:Int

            nextID=if(currentIdNumber==null){
                1
            }else{
                currentIdNumber.toInt()+1
            }

            val notes=Notes()
            notes.title=titleED.text.toString()
            notes.description=descriptionED.text.toString()
            notes.id=nextID.toString()

            //copy this transaction and commit

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this,"notes added succesfully",Toast.LENGTH_LONG).show()

            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }catch (e:Exception){
            Toast.makeText(this,"failed $e",Toast.LENGTH_LONG).show()
        }
    }
}