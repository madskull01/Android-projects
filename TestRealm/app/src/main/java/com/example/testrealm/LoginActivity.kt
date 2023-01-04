package com.example.testrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmModel
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        edtEmail=findViewById(R.id.edtEmail)
        edtPassword=findViewById(R.id.edtPassword)
        btnLogin=findViewById(R.id.btnLogin)
        btnSignUp=findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener { login() }

        btnSignUp.setOnClickListener {
            val intent= Intent(this,SignUpActivity::class.java)

            startActivity(intent)
        }




    }
    private fun validateCredentials(): Boolean = when {
        // zero-length usernames and passwords are not valid (or secure), so prevent users from creating accounts with those client-side.
        edtEmail.text.toString().isEmpty() -> false
        edtPassword.text.toString().isEmpty() -> false
        else -> true
    }
    private fun login(){
        if (!validateCredentials()) {
            Toast.makeText(this,"invalid credentials",Toast.LENGTH_LONG).show()
        }

        val edtEmail = this.edtEmail.text.toString()
        val edtPassword = this.edtPassword.text.toString()
        val creds = Credentials.emailPassword(edtEmail, edtPassword)
        taskApp.loginAsync(creds) {
            // re-enable the buttons after user login returns a result

            if (!it.isSuccess) {
                Toast.makeText(this,"login unsuccesful",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Succesfully login",Toast.LENGTH_LONG).show()
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}