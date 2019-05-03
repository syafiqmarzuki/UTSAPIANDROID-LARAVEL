package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model.PemainModel
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.MoreActivity
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.R
import kotlinx.android.synthetic.main.list_item_pemain.view.*

class PemainAdapter (private val mList : MutableList<PemainModel>, private val mContext : Context) : RecyclerView.Adapter<PemainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_item_pemain, p0, false))

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bindData(mList[p1], mContext)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindData(model : PemainModel, context: Context){
            itemView.setOnClickListener {
                val i = Intent(context, MoreActivity::class.java)
                i.putExtra("ID", model.id)
                i.putExtra("NAME", model.name)
                i.putExtra("DESC", model.description)
                i.putExtra("IMAGE", model.image)
                context.startActivity(i)
            }
            //itemView.list_i_desc.text = model.description
            itemView.list_i_name.text = model.name
            Glide.with(context).load("https://apibola.herokuapp.com/uploads/"+model.image).into(itemView.list_i_image)
        }
    }

}