package com.example.sankey_welcome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sankeyinfo.*

class SankeyInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sankeyinfo)


        val name = intent.getStringExtra("name")
        txt_welcome.text = "Welcome to Sankey Solution\n${name}!"
    }


}