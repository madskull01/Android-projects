package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


class EditNoteActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel

    var noteID = -1



    lateinit var noteTitleEdt: EditText
    lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        noteTitleEdt = findViewById(R.id.edit)
        saveBtn = findViewById(R.id.updateButton)



        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get<NoteViewModel>(NoteViewModel::class.java)



        // on below line we are setting data to edit text.
        val noteTitle = intent.getStringExtra("noteTitle")
        noteID = intent.getIntExtra("noteId", -1)
        noteTitleEdt.setText(noteTitle)


        // on below line we are adding
        // click listener to our save button.
        saveBtn.setOnClickListener {
            // on below line we are getting
            // title and desc from edit text.
            val noteTitle = noteTitleEdt.text.toString()
            // on below line we are checking the typenj
            3
            // and then saving or updating the data.

            if (noteTitle.isNotEmpty()) {
                val updatedNote = Note(noteTitle)
                updatedNote.id = noteID
                viewModel.updateNote(updatedNote)
            }

            // opening the new activity on below line
            val intent= Intent(applicationContext, MainActivity::class.java)

            startActivity(intent)
            this.finish()
            Toast.makeText(this," $noteTitle updated in the list",Toast.LENGTH_LONG).show()


        }

    }
}