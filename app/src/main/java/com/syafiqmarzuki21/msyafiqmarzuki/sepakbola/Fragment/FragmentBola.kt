package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Fragment
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.MainActivity
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model.PemainModel
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network.WrappedResponse
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.R
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Utilities.ApiUtils
import kotlinx.android.synthetic.main.fragment_new.*

import kotlinx.android.synthetic.main.fragment_new.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URI

class FragmentBola  : Fragment()  {
    private val REQ_CODE_IMAGE = 12
    private var pemain = PemainModel()
    private val TAG = "FragPem"
    private var pemainService = ApiUtils.pemService()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_new, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.fab_image.setOnClickListener { Pix.start(this@FragmentBola, Options.init().setRequestCode(REQ_CODE_IMAGE)) }

        view.btnSave.setOnClickListener {
            pemain.name = etName.text.toString().trim()
            pemain.description = etDesc.text.toString().trim()

            if(pemain.name != null && pemain.description != null && pemain.image != null){
                view.progress_upload.visibility = View.VISIBLE
                view.btnSave.isEnabled = false

                val file = File(pemain.image)
                val requestBodyForFile = RequestBody.create(MediaType.parse("image/*"), file)
                val image : MultipartBody.Part = MultipartBody.Part.createFormData("image",file.name,requestBodyForFile)
                val name : RequestBody = RequestBody.create(MultipartBody.FORM, pemain.name.toString())
                val desc : RequestBody = RequestBody.create(MultipartBody.FORM, pemain.description.toString())
                val req = pemainService.new(image, name, desc)
                req.enqueue(object : Callback<WrappedResponse<PemainModel>>{
                    override fun onFailure(call: Call<WrappedResponse<PemainModel>>, t: Throwable) {
                        view.btnSave.isEnabled = true
                        view.progress_upload.visibility = View.INVISIBLE
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        Log.d(TAG, "onFailure"+t.message)
                    }

                    override fun onResponse(call: Call<WrappedResponse<PemainModel>>, response: Response<WrappedResponse<PemainModel>>) {
                        if(response.isSuccessful){
                            val b = response.body()
                            if(b != null){
                                view.etName.text.clear()
                                view.etDesc.text.clear()
                                pemain.name = null
                                pemain.description = null
                                pemain.image = null
                                Glide.with(context!!).load(R.drawable.ic_camera_white_24dp).into(view.image_preview)
                                Toast.makeText(context, "Successfullly uploaded", Toast.LENGTH_SHORT).show()

                            }
                        }else{
                            Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()
                            Log.d(TAG, response.message())
                        }
                        view.btnSave.isEnabled = true
                        view.progress_upload.visibility = View.INVISIBLE
                    }
                })
            }else{

                pemain.name = etName.text.toString()
                pemain.description = etDesc.text.toString().trim()

                if(TextUtils.isEmpty(pemain.name)){
                    etName.setError("Nama diperlukan!");
                }

                if(TextUtils.isEmpty(pemain.description)){
                    etDesc.setError("Deskripsi diperlukan!");
                }





            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            pemain.image = returnValue[0]
            Toast.makeText(context!!, pemain.image, Toast.LENGTH_LONG).show()
            println(Log.d(TAG, pemain.image))
            Glide.with(context!!).load(pemain.image).into(view!!.image_preview)
        }
    }
}