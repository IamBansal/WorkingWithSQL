package com.example.workingwithsql

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class CustomItemAdapter(private var context: Context, private var data: ArrayList<CustomItem>) : RecyclerView.Adapter<CustomItemAdapter.ViewHolder>() {

    private var DB = SQLHelper(context)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.tvTitle)
        var desc: TextView = itemView.findViewById(R.id.tvDesc)
        var id: TextView = itemView.findViewById(R.id.tvID)
        var deleteButton: Button = itemView.findViewById(R.id.btnDelete)
        var card : CardView = itemView.findViewById(R.id.idCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.text = data[position].title
        holder.desc.text = data[position].desc
        holder.id.text = data[position].id
        holder.deleteButton.setOnClickListener {

            //For deleting and refreshing the page, we have to delete the item from database and from list too.

            //Deleting from database
            DB.deleteData(data[position].id)

            //deleting from list
            data.remove(data[position])

            //notifying that it is removed.
            notifyItemRemoved(position)
        }

        holder.card.setOnLongClickListener {

            val alert = AlertDialog.Builder(context)
            alert.setTitle("Delete Note")
            alert.setMessage("You want to delete it?")
            alert.setPositiveButton("Delete"){_,_ ->
                DB.deleteData(data[position].id)
                data.remove(data[position])
                notifyItemRemoved(position)
            }
            alert.show()
            return@setOnLongClickListener true
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

}