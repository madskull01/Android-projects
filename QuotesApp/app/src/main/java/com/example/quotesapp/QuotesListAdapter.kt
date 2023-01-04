package com.example.quotesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuotesListAdapter(private val context: Context, private val list: List<QuotesResponse>,private val listener: CopyListener)
    : RecyclerView.Adapter<QuotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val layout=LayoutInflater.from(context).inflate(R.layout.list_quotes,parent,false)
        return QuotesViewHolder(layout)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.txtQuote.text= list[position].text
        holder.txtAuthor.text=list[position].author
        holder.buttonCopy.setOnClickListener {
            listener.onCopyClicked(list[holder.adapterPosition].text)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtQuote:TextView=itemView.findViewById(R.id.txtQuote)
    var txtAuthor:TextView=itemView.findViewById(R.id.txtAuthor)
    var buttonCopy:Button=itemView.findViewById(R.id.buttonCopy)
}