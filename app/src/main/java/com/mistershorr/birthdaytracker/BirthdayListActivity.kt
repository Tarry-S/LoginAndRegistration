package com.mistershorr.birthdaytracker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mistershorr.birthdaytracker.databinding.ActivityBirthdayListBinding
import com.backendless.exceptions.BackendlessFault

import com.backendless.async.callback.AsyncCallback

import com.backendless.Backendless




class BirthdayListActivity : AppCompatActivity() {

    lateinit var binding : ActivityBirthdayListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //load data from database
        //but it into the recyclerView
        loadDataFromBackendless()
    }

    private fun loadDataFromBackendless() {
        Backendless.Data.of(Person::class.java).find(object : AsyncCallback<List<Person?>?> {
            override fun handleResponse(foundPeople: List<Person?>?) {
                // all Person instances have been found
                Log.d("BirthdayList", "handleResponse : ${foundPeople}")
                val adapter = BirthdayAdapter((foundPeople ?: listOf<Person>()) as List<Person>)
                binding.recyclerViewBirthdayList.adapter = adapter
                binding.recyclerViewBirthdayList.layoutManager =
                    LinearLayoutManager(this@BirthdayListActivity)
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("BirthdayList", "handleFault : ${fault.message}")
            }
        })
    }
}