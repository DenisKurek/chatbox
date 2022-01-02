package com.denis.kolokwium2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.raw_items.view.*

class MyAdapter(val context: Context,val userList:List<MyDataItem>): RecyclerView.Adapter<MyAdapter.viewHolder>(){
    class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var login: TextView
        var content: TextView
        var date: TextView
        var id: Int
        init{
            login=itemView.login
            date = itemView.data
            content= itemView.content
            id = itemView.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.raw_items,parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.login.text=userList[position].login.toString()
        holder.date.text=userList[position].date.toString()
        holder.content.text=userList[position].content.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}