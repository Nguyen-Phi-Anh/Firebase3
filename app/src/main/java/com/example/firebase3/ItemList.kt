package com.example.firebase3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ItemList : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList:ArrayList<itemDs>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        itemRecyclerView = findViewById(R.id.item_list)
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.hasFixedSize()
        itemArrayList = arrayListOf<itemDs>()
        getItemData()
    }

    private fun getItemData() {
        db = FirebaseDatabase.getInstance().getReference("sanpham")
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (itmsnapshot in snapshot.children){
                        val item = itmsnapshot.getValue(itemDs::class.java)
                        itemArrayList.add(item!!)
                    }
                    itemRecyclerView.adapter = itmAdapter(itemArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}