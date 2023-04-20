package com.example.firebase3

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class itmAdapter(private val itmlst:ArrayList<itemDs>):RecyclerView.Adapter<itmAdapter.itmHolder>() {
    class itmHolder(itmView: View):RecyclerView.ViewHolder(itmView){
        val tensp: EditText= itmView.findViewById(R.id.TenSP)
        val loaisp: EditText= itmView.findViewById(R.id.LoaiSP)
        val giasp: EditText= itmView.findViewById(R.id.GiaSP)
        val itmimg:ImageView = itmView.findViewById(R.id.itm_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,
        parent,false)
        return itmHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itmlst.size
    }

    override fun onBindViewHolder(holder: itmHolder, position: Int) {
        val currentitem = itmlst[position]
        holder.tensp.setText(currentitem.TenSP.toString())
        holder.loaisp.setText(currentitem.LoaiSP.toString())
        holder.giasp.setText(currentitem.GiaSP.toString())
        val bytes = android.util.Base64.decode(currentitem.Img,android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        holder.itmimg.setImageBitmap(bitmap)
    }
}