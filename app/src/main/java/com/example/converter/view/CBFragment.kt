package com.example.converter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.model.CBApiResponse
import com.example.converter.model.RecyclerAdapter
import com.example.converter.network.CBService
import com.example.converter.network.RetrofitInstance

class CBFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val service = CBService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cb, container, false)

        lifecycleScope.launchWhenCreated {
            val response = try{
                RetrofitInstance.api.getUserData()
            }catch (e: Exception){
                Log.e("RetrofitError", "Exception: $e")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                val usersRecyclerView = rootView.findViewById<RecyclerView>(R.id.main_recycler).apply {
                    adapter = RecyclerAdapter(){it}
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                }
                val cbr: CBApiResponse = response.body()!!

                (usersRecyclerView.adapter as RecyclerAdapter).submitList(ArrayList(cbr.valute.values))
                Log.d("ResponseBody", response.body().toString())
            } else {
                Log.e("ResponseCBError", "Запрос не вернул результат")
            }
        }
        return rootView
    }
}