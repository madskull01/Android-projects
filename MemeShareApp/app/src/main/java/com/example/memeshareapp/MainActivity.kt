package com.example.memeshareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var share: Button
    private lateinit var next: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()

        imageView = findViewById(R.id.memeImageView)
        share = findViewById(R.id.shareButton)
        next = findViewById(R.id.nextButton)

        next.setOnClickListener {
            loadMeme()
        }
        share.setOnClickListener { }
    }

    private fun loadMeme() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                val url=response.getString("url")
                Glide.with(this).load(url).into(imageView)
            },
            {  })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}