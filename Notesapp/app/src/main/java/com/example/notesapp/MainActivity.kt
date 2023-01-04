package com.example.notesapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), INotesRVAdapter{

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter =adapter



        Log.d("MainActivity","console123")
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get<NoteViewModel>(NoteViewModel::class.java)



        Log.d("MainActivity","console1234")
        viewModel.allNotes.observe(this, Observer{list->
            list?.let{
                adapter.updateList(it)

            }
        })
        Log.d("MainActivity","console1235")
    }



    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)






    }

    fun submitData(view: View) {
        val noteText = input.text.toString()

        if (noteText.isNotEmpty()){
            Log.d("main Activity","noteText"+noteText)

            viewModel.insertNote(Note(noteText))
//            Toast.makeText(this,"deleted",Toast.LENGTH_LONG).show()

            input.setText("")
            Toast.makeText(this," $noteText added in the list", Toast.LENGTH_LONG).show()


        }

    }
    override fun onNoteClick(note: Note) {
        // opening a new intent and passing a data to it.

        val intent = Intent(this@MainActivity, EditNoteActivity::class.java)

        intent.putExtra("noteTitle", note.text)
        intent.putExtra("noteId", note.id)
        startActivity(intent)

        this.finish()




    }
}