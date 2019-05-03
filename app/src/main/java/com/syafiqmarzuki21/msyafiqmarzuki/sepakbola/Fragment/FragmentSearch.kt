package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Fragment
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Adapter.PemainAdapter
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model.PemainModel
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network.WrappedListResponse
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.R
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Utilities.ApiUtils

import kotlinx.android.synthetic.main.bottom_bar_search.view.*
import kotlinx.android.synthetic.main.etc_empty_data.view.*
import kotlinx.android.synthetic.main.etc_empty_trouble.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentSearch : Fragment() {
    private val pemainService = ApiUtils.pemService()
    private var pemainList = mutableListOf<PemainModel>()
    private val TAG = "FragSearch"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(
        R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.rvPemainSearch.layoutManager = LinearLayoutManager(activity)
        view.et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!view.et_search.text.toString().trim().isEmpty()){
                        runFilter(view.et_search.text.toString().trim())
                    }
                    return true
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        pemainList.clear()
        if(!view!!.et_search.text.toString().trim().isEmpty()){
            runFilter(view!!.et_search.text.toString().trim())
        }
    }

    private fun runFilter(query : String){
        pemainList.clear()
        val request = pemainService.search(query)
        view!!.loading.visibility = View.VISIBLE
        view!!.empty_view_data.visibility = View.INVISIBLE
        view!!.empty_view_trouble.visibility = View.INVISIBLE
        request.enqueue(object : Callback<WrappedListResponse<PemainModel>>{
            override fun onFailure(call: Call<WrappedListResponse<PemainModel>>, t: Throwable) {
                Log.d(TAG, t.message)
                view!!.loading.visibility = View.INVISIBLE
                view!!.empty_view_trouble.visibility = View.VISIBLE
                view!!.empty_view_data.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<WrappedListResponse<PemainModel>>, response: Response<WrappedListResponse<PemainModel>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.status == 1){
                            view!!.empty_view_data.visibility = View.INVISIBLE
                            view!!.empty_view_trouble.visibility = View.INVISIBLE
                            pemainList = body.data as MutableList
                            view!!.rvPemainSearch.adapter = PemainAdapter(pemainList, context!!)
                        }else{
                            view!!.rvPemainSearch.adapter?.notifyDataSetChanged()
                            view!!.empty_view_data.visibility = View.VISIBLE
                            view!!.empty_view_trouble.visibility = View.INVISIBLE
                            Toast.makeText(context, body.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    view!!.empty_view_data.visibility = View.INVISIBLE
                    view!!.empty_view_trouble.visibility = View.VISIBLE
                    Log.d(TAG, "Something went wrong")
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                view!!.loading.visibility = View.INVISIBLE
            }
        })
    }
}