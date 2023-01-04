package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class HomeActivity : AppCompatActivity() {
    // creating a variable
    // for our text view..
    private  lateinit var userNameTV: TextView

    // button for logout
    private lateinit var logoutBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        logoutBtn = findViewById(R.id.idBtnLogout)

        // initializing our variables
        userNameTV = findViewById(R.id.idTVUserName)

        // getting data from intent.
        val name = intent.getStringExtra("username")

        // setting data to our text view.
        userNameTV.setText(name)

        // initializing click listener for logout button
        logoutBtn.setOnClickListener(View.OnClickListener { // calling a method to logout our user.
            ParseUser.logOutInBackground { e ->
                if (e == null) {
                    Toast.makeText(this@HomeActivity, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@HomeActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
        })
    }
}

