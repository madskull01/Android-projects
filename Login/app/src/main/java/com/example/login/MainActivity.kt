package com.example.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser


class MainActivity : AppCompatActivity() {
    // creating variables for our edit text and buttons.
    private lateinit var userNameEdt: EditText
    private lateinit var passwordEdt: EditText
    private lateinit var loginBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing our edit text and buttons.
        userNameEdt = findViewById(R.id.idEdtUserName)
        passwordEdt = findViewById(R.id.idEdtPassword)
        loginBtn = findViewById(R.id.idBtnLogin)

        // adding on click listener for our button.
        loginBtn.setOnClickListener(View.OnClickListener { // on below line we are getting data from our edit text.
            val userName = userNameEdt.getText().toString()
            val password = passwordEdt.getText().toString()

            // checking if the entered text is empty or not.
            if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter user name and password",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // calling a method to login our user.
            loginUser(userName, password)
        })
    }

    private fun loginUser(userName: String, password: String) {
        // calling a method to login a user.
        ParseUser.logInInBackground(userName, password) { parseUser, e ->
            // after login checking if the user is null or not.
            if (parseUser != null) {
                // if the user is not null then we will display a toast message
                // with user login and passing that user to new activity.
                Toast.makeText(this, "Login Successful ", Toast.LENGTH_SHORT).show()
                val i = Intent(this@MainActivity, HomeActivity::class.java)
                i.putExtra("username", userName)
                startActivity(i)
            } else {
                // display an toast message when user logout of the app.
                ParseUser.logOut()
                Toast.makeText(this@MainActivity, e.getMessage(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
