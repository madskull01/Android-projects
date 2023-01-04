package com.example.bookhub.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntity
import com.example.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    private lateinit var txtBookName:TextView
    private lateinit var txtBookAuthor:TextView
    private lateinit var txtBookPrice:TextView
    private lateinit var txtBookRating:TextView
    private lateinit var txtBookDesc:TextView
    private lateinit var imgBookImage:ImageView
    private lateinit var btnAddToFav:Button
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: Toolbar

    var bookId:String?="100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtBookAuthor=findViewById(R.id.txtBookAuthor)
        txtBookDesc=findViewById(R.id.txtBookDesc)
        txtBookName=findViewById(R.id.txtBookName)
        txtBookPrice=findViewById(R.id.txtBookPrice)
        txtBookRating=findViewById(R.id.txtBookRating)
        imgBookImage=findViewById(R.id.imgBookImage)
        btnAddToFav=findViewById(R.id.btnAddToFav)
        progressBar=findViewById(R.id.progressBar)
        progressLayout=findViewById(R.id.progressLayout)
        toolbar=findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Details"

        progressLayout.visibility= View.VISIBLE
        progressBar.visibility=View.VISIBLE

        if (intent!=null){
            bookId= intent.getStringExtra("book_id")
        }else{
            finish()
            Toast.makeText(this,"some unexpected error ocured",Toast.LENGTH_SHORT).show()
        }

        if (bookId=="100"){
            finish()
            Toast.makeText(this,"some unexpected error ocured",Toast.LENGTH_SHORT).show()


        }

        val queue= Volley.newRequestQueue(this@DescriptionActivity)
        val url="http://13.235.250.119/v1/book/get_book/"
        val jsonParams=JSONObject()
        jsonParams.put("book_id",bookId)

        if (ConnectionManager().checkConnectivity(this)){
            val jsonRequest=object : JsonObjectRequest(Request.Method.POST,url,jsonParams, Response.Listener {
                try {
                    val success=it.getBoolean("success")
                    if (success){
                        val bookJsonObject=it.getJSONObject("book_data")
                        progressLayout.visibility=View.GONE
                        val bookImageUrl=bookJsonObject.getString("image")
                        Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(imgBookImage)
                        txtBookName.text=bookJsonObject.getString("name")
                        txtBookAuthor.text=bookJsonObject.getString("author")
                        txtBookPrice.text=bookJsonObject.getString("price")
                        txtBookRating.text=bookJsonObject.getString("rating")
                        txtBookDesc.text=bookJsonObject.getString("description")

                        val bookEntity=BookEntity(
                        bookId?.toInt() as Int,
                            txtBookName.text.toString(),
                            txtBookAuthor.text.toString(),
                            txtBookPrice.text.toString(),
                            txtBookRating.text.toString(),
                            txtBookDesc.text.toString(),
                            bookImageUrl
                        )

                        val checkFav =DBAsyncTask(applicationContext,bookEntity,1).execute()
                        val isFav=checkFav.get()

                        if (isFav){
                            btnAddToFav.text="Remove from favorite"
                            val favColor= ContextCompat.getColor(applicationContext,R.color.favorite)
                            btnAddToFav.setBackgroundColor(favColor)
                        }else{
                            btnAddToFav.text="Add to favorite"
                            val noFavColor=ContextCompat.getColor(applicationContext,R.color.purple_700)
                            btnAddToFav.setBackgroundColor(noFavColor)
                        }

                        btnAddToFav.setOnClickListener {
                            if (!DBAsyncTask(applicationContext,bookEntity,1).execute().get()){
                                val async=DBAsyncTask(applicationContext,bookEntity,2).execute()
                                val result=async.get()
                                if (result){
                                    Toast.makeText(
                                        this,"Book is added to favorites",
                                        Toast.LENGTH_SHORT).show()
                                    btnAddToFav.text="Remove from favorites"
                                    val favColor=ContextCompat.getColor(applicationContext,R.color.favorite)
                                    btnAddToFav.setBackgroundColor(favColor)
                                }else{
                                    Toast.makeText(this,"some error occurred",Toast.LENGTH_SHORT).show()
                                }

                            }else{
                                val async=DBAsyncTask(applicationContext,bookEntity,3).execute()
                                val result =async.get()

                                if (result){
                                    Toast.makeText(this,"book removed from favorites",Toast.LENGTH_SHORT).show()
                                    btnAddToFav.text="Add to favorites"
                                    val noFavColor=ContextCompat.getColor(applicationContext,R.color.purple_700)
                                    btnAddToFav.setBackgroundColor(noFavColor)
                                }else{
                                    Toast.makeText(this,"some error occurred",Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }else{
                        Toast.makeText(this,"some error occurred",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Toast.makeText(this,"some error occurred",Toast.LENGTH_SHORT).show()

                }
            },Response.ErrorListener {
                Toast.makeText(this,"Volley error ocured",Toast.LENGTH_SHORT).show()

            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["Content-Type"]="application/json"
                    headers["token"]="8b9f6d060dac08"
                    return headers
                }
            }
            queue.add(jsonRequest)
        }else{
            val dialog= AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings"){text,listener->
                val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit App"){text,listener->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()
        }


    }
    class DBAsyncTask(private val context: Context, private val bookEntity: BookEntity, private val mode:Int):AsyncTask<Void,Void,Boolean>(){
        /*
        mode 1->check db if book is favorite or not
        mode 2->save the book in the db as favorite
        mode 3->Remove the favorite book
        * */
        val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode){
                1->{
                    //check db if book is favorite or not
                    val book:BookEntity?=db.bookDao().getBookById(bookEntity.book_Id.toString())
                    db.close()
                    return book!=null
                }
                2->{
                    //save the book in the db as favorite
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3->{
                    //remove the favorite book
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}