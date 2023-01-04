package com.example.quotesapp

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dialog:ProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("msg","log1")
        val random =RequestManager()
        Log.d("msg","log2")
        random.getAllQuotes(listener)

        dialog= ProgressDialog(this)
        Log.d("msg","log3")
        dialog?.setTitle("Loading...")
        dialog?.show()

    }
    private val listener:QuotesResponseListener=object :QuotesResponseListener{
        override fun didFetch(response: List<QuotesResponse>, massage: String) {
            dialog?.dismiss()
            recycler_home.setHasFixedSize(true)
            recycler_home.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            val adapter=QuotesListAdapter(this@MainActivity,response,copyListener)
            recycler_home.adapter=adapter

        }

        override fun didError(message: String) {
            dialog?.dismiss()
            Log.d("msg","log2.7")
            Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
        }
    }
    private val copyListener:CopyListener=object :CopyListener{
        override fun onCopyClicked(text: String) {
            val clipboard=getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip=ClipData.newPlainText("Copied Data",text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@MainActivity,"Quote copied to clipboard",Toast.LENGTH_SHORT).show()
        }

    }
}