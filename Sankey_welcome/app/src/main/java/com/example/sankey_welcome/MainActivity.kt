package com.example.sankey_welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var edtname:EditText
    private lateinit var btnregister:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtname=findViewById(R.id.edtname)
        btnregister=findViewById(R.id.btnregister)


        btnregister.setOnClickListener {
            val name = edtname.text.toString()

            val intent = Intent(this,SankeyInfoActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
            Toast.makeText(this,"Successfully Register",Toast.LENGTH_SHORT).show()

            }
        }


    }
