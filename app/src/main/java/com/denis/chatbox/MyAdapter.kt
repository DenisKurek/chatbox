package com.denis.chatbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.raw_items.view.*

class MyAdapter(
    val context: Context,
    val userList:ArrayList<MyDataItem>,
    private val listener: OnItemclickListener
    ): RecyclerView.Adapter<MyAdapter.viewHolder>(){
    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var login: TextView
        var content: TextView
        var date: TextView
        //var id: Int
        init{
            login=itemView.login
            date = itemView.data
            content= itemView.content
            //id = itemView.id
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val myDataItem:MyDataItem =userList[adapterPosition]
            listener.onItemClick(myDataItem.login,
                myDataItem.date,
                myDataItem.id,
                myDataItem.content
            )
        }
    }
    interface OnItemclickListener{
        fun onItemClick(login:String,date:String,id:String,content: String)
    }
    fun deleteItem(ind: Int){
       userList.removeAt(ind)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.raw_items,parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.login.text=userList[position].login
        holder.date.text=userList[position].date
        holder.content.text=userList[position].content
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}