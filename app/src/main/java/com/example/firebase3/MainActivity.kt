package com.example.firebase3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebase3.databinding.ActivityMainBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    var sImage:String? =""
    private lateinit var db : DatabaseReference
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun insert_data(view: View) {
        val TenSP = binding.edtName.text.toString()
        val LoaiSP = binding.edtLoaiSP.text.toString()
        val GiaSP = binding.edtGiaSP.text.toString()
        db = FirebaseDatabase.getInstance().getReference("sanpham")
        val item = itemDs(TenSP, LoaiSP, GiaSP, sImage)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        db.child(id.toString()).setValue(item).addOnCanceledListener {
            binding.edtName.text.clear()
            binding.edtLoaiSP.text.clear()
            sImage = ""
            Toast.makeText(this,"data inesrt",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"data not insert",Toast.LENGTH_SHORT).show()
        }
    }
    fun insert_img(view: View) {
        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        ActivityResultLauncher.launch(myfileintent)
    }
    private val ActivityResultLauncher = registerForActivityResult<Intent,ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){result:ActivityResult->
        if(result.resultCode== RESULT_OK){
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                val bytes = stream.toByteArray()
                sImage = Base64.encodeToString(bytes,Base64.DEFAULT)
                binding.imageView.setImageBitmap(myBitmap)
                inputStream!!.close()
                Toast.makeText(this, "Image select", Toast.LENGTH_SHORT).show()

            }catch (ex: Exception){
                Toast.makeText(this,ex.message.toString(),Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun showList(view: View) {
        var i: Intent
        i = Intent(this, ItemList::class.java)
        startActivity(i)
    }
}