package com.example.quotesapp

interface QuotesResponseListener {
    fun didFetch(response: List<QuotesResponse>,massage:String)
    fun didError(message:String)
}