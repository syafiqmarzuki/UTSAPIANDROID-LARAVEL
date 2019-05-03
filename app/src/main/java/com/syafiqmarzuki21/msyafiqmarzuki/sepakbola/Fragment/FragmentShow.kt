package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Fragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Adapter.PemainAdapter
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model.PemainModel
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network.WrappedListResponse
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.R
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Utilities.ApiUtils

import kotlinx.android.synthetic.main.etc_empty_data.view.*
import kotlinx.android.synthetic.main.etc_empty_trouble.view.*
import kotlinx.android.synthetic.main.fragment_show.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentShow : Fragment() {
    private var pemainService = ApiUtils.pemService()
    private var pemainList = mutableListOf<PemainModel>()
    private val TAG = "FragShow"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(
        R.layout.fragment_show, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.rvPemain.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onResume() {
        super.onResume()
        pemainList.clear()
        loadData()
    }

    private fun loadData(){
        val request = pemainService.all()
        view!!.loading.visibility = View.VISIBLE
        view!!.empty_view_trouble.visibility = View.INVISIBLE
        view!!.empty_view_data.visibility = View.INVISIBLE
        request.enqueue(object : Callback<WrappedListResponse<PemainModel>>{
            override fun onFailure(call: Call<WrappedListResponse<PemainModel>>, t: Throwable) {
                Log.d(TAG, t.message)
                view!!.loading.visibility = View.INVISIBLE
                view!!.empty_view_trouble.visibility = View.VISIBLE
                view!!.empty_view_data.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<WrappedListResponse<PemainModel>>, response: Response<WrappedListResponse<PemainModel>>) {
                if(response.isSuccessful){
                    val r = response.body()
                    if(r != null){
                        if(r.status == 1){
                            Log.d(TAG, r.message)
                            view!!.empty_view_trouble.visibility = View.INVISIBLE
                            view!!.empty_view_data.visibility = View.INVISIBLE
                            pemainList = r.data as MutableList<PemainModel>
                            if(pemainList.isEmpty()){
                                view!!.rvPemain.adapter?.notifyDataSetChanged()
                                view!!.empty_view_data.visibility = View.VISIBLE
                            }else{ view?.rvPemain?.adapter = PemainAdapter(pemainList, context!!) }
                        }else{
                            view!!.rvPemain.adapter?.notifyDataSetChanged()
                            view!!.empty_view_trouble.visibility = View.INVISIBLE
                            view!!.empty_view_data.visibility = View.VISIBLE
                        }
                    }
                }else{
                    view!!.empty_view_trouble.visibility = View.VISIBLE
                    view!!.empty_view_data.visibility = View.INVISIBLE
                    Log.d(TAG, "Response is not success")
                }
                view!!.loading.visibility = View.INVISIBLE
            }
        })
    }
}