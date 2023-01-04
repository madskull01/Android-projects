package com.example.menututorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
//    private lateinit var firstButton:Button
//    private lateinit var secondButton:Button
//    private lateinit var thirdButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstButton=findViewById<Button>(R.id.firstButton)
        val secondButton=findViewById<Button>(R.id.secondButton)
        val thirdButton=findViewById<Button>(R.id.thirdButton)
        val nextButton=findViewById<Button>(R.id.nextButton)




        val addContactDialog=AlertDialog.Builder(this)
            .setTitle("Add contact")
            .setMessage("Do you want to add Mr rohit to your to contacts list?")
            .setIcon(R.drawable.ic_add_contact)
            .setPositiveButton("yes"){_,_->
                Toast.makeText(this,"you added me rohit to your contact list",Toast.LENGTH_LONG).show()

            }
            .setNegativeButton("no"){_,_->
                Toast.makeText(this,"you didn't added me rohit to your contact list",Toast.LENGTH_LONG).show()

            }.create()

        firstButton.setOnClickListener {
            Toast.makeText(this,"hello",Toast.LENGTH_LONG).show()
            addContactDialog.show()
         }

        val options= arrayOf("First item","second item","third item")
        val singleChoiceDialog=AlertDialog.Builder(this)
            .setTitle("Choose one of these options")
            .setSingleChoiceItems(options,0){dialogInterface,i->
                Toast.makeText(this,"you clicked on${options[i]}",Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Accept"){_,_->
                Toast.makeText(this,"You accepted the singleChoiseDialog",Toast.LENGTH_LONG).show()

            }
            .setNegativeButton("no"){_,_->
                Toast.makeText(this,"You declined the singlechoiseDialog",Toast.LENGTH_LONG).show()

            }.create()

        secondButton.setOnClickListener {
            singleChoiceDialog.show()
        }

    val multiChoiceDialog=AlertDialog.Builder(this)
        .setTitle("Choose one of these options")
        .setMultiChoiceItems(options, booleanArrayOf(false,false,false)){_,i,isCheked->
            if (isCheked){
                Toast.makeText(this,"YOu checked ${options[i]}",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"you unchecked ${options[i]}",Toast.LENGTH_LONG).show()
            }

        }
        .setPositiveButton("Accept"){_,_->
            Toast.makeText(this,"You accepted the multiChoiseDialog",Toast.LENGTH_LONG).show()

        }
        .setNegativeButton("no"){_,_->
            Toast.makeText(this,"You declined the multiChoiseDialog",Toast.LENGTH_LONG).show()

        }.create()


        thirdButton.setOnClickListener {
            multiChoiceDialog.show()
        }

        nextButton.setOnClickListener {
            val intent= Intent(this,SpinnerActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miAddContact->Toast.makeText(this,"clicked on add contact",Toast.LENGTH_LONG).show()
            R.id.miFavorites->Toast.makeText(this,"clicked on add favourites",Toast.LENGTH_LONG).show()
            R.id.miSettings->Toast.makeText(this,"clicked on add settings",Toast.LENGTH_LONG).show()
            R.id.miClose->finish()
            R.id.miFeedback->Toast.makeText(this,"clicked on add feedback",Toast.LENGTH_LONG).show()
        }
        return true
    }
}