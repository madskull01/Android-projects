package com.example.notesrealm

import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class random {
    fun deleteNote(id: String?) =GlobalScope.launch(Dispatchers.IO){
        val realm= Realm.getDefaultInstance()
        val notes = realm.where(Notes::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            notes!!.deleteFromRealm()
        }
    }
}