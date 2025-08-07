package com.example.musicapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    lateinit var  myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.musicapp.R.layout.activity_main) // use correct R
        myRecyclerView = findViewById(R.id.recyclerView)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/") // put base URL inside quotes
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("eminem") // correct function call syntax

        retrofitData.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                val dataList: List<Data> = response.body()?.data!!
              //  var textView = findViewById<TextView>(R.id.hellowtext)
                //textView.text = dataList.toString()


                myAdapter = MyAdapter(this@MainActivity, datalist = dataList)
                myRecyclerView.adapter = myAdapter

                myRecyclerView.layoutManager = LinearLayoutManager( this@MainActivity)
                Log.d("TAG: onResponse", "onResponse: " + response.body())
                // You can do something with dataList here
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                // Handle failure here
            }
        })
    }
}
