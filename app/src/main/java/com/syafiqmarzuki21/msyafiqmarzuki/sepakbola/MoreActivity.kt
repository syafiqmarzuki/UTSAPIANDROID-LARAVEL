package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model.PemainModel
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network.WrappedResponse
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Utilities.ApiUtils
import kotlinx.android.synthetic.main.activity_more.*
import kotlinx.android.synthetic.main.content_more.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreActivity : AppCompatActivity() {

    private var pemain = PemainModel()
    private var pemainService = ApiUtils.pemService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        getIntentData()
        fetchData()
        fab.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure to delete this item?")
            builder.setPositiveButton("DELETE") { _, which ->
                val req = pemainService.delete(pemain.id.toString())
                req.enqueue(object : Callback<WrappedResponse<PemainModel>> {
                    override fun onFailure(call: Call<WrappedResponse<PemainModel>>, t: Throwable) { Toast.makeText(this@MoreActivity, t.message, Toast.LENGTH_LONG).show() }
                    override fun onResponse(call: Call<WrappedResponse<PemainModel>>, response: Response<WrappedResponse<PemainModel>>) {
                        if(response.isSuccessful){
                            val b = response.body()
                            if(b != null && b.status == 1){
                                Toast.makeText(this@MoreActivity, "Successfully deleted", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }else{
                            Toast.makeText(this@MoreActivity, "Something went wrong", Toast.LENGTH_LONG).show()
                        }
                    }
                })
                finish()
            }
            builder.setNegativeButton("CANCEL") { dialog, _ -> dialog.cancel() }
            builder.setCancelable(false)
            builder.show()

        }
    }

    private fun getIntentData(){
        pemain.id = intent.getIntExtra("ID", 0)
        pemain.name = intent.getStringExtra("NAME")
        pemain.description = intent.getStringExtra("DESC")
        pemain.image = intent.getStringExtra("IMAGE")
    }

    private fun fetchData(){
        detail_name.text = pemain.name
        detail_desc.text = pemain.description
        Glide.with(applicationContext).load("https://apibola.herokuapp.com/uploads/${pemain.image}").into(detail_image)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_more,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.action_settings -> { true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
