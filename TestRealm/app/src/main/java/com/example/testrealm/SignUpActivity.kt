package com.example.testrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm

class SignUpActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var projectRealm:Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtEmail=findViewById(R.id.edtEmail)
        edtPassword=findViewById(R.id.edtPassword)
        edtName=findViewById(R.id.edtName)
        btnSignUp=findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val name=edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            signup(name,email,password)
        }
    }
    private fun signup(name:String, email:String,password:String){
        taskApp.emailPassword.registerUserAsync(email, password) {
            // re-enable the buttons after user registration returns a result
            if (!it.isSuccess) {
                Toast.makeText(this,"registeration unsuccesfull",Toast.LENGTH_LONG).show()
            } else {

                // when the account has been created successfully, log in to the account
                Toast.makeText(this,"registration succesfull",Toast.LENGTH_LONG).show()
                val user = UserInfo(edtEmail.text.toString())
                // all realm writes need to occur inside of a transaction
                projectRealm.executeTransactionAsync { realm ->
                    realm.copyToRealmOrUpdate(user)
                }
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}