package com.sergisirvent.myf1app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.data.driver.DriversDataImpl
import com.sergisirvent.myf1app.data.remote.MyF1AppService
import com.sergisirvent.myf1app.databinding.ActivityGeneralTabBinding
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.presentation.fragment.GeneralTabFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class GeneralTabActivity : AppCompatActivity(
) {

    private val binding: ActivityGeneralTabBinding by lazy {
        ActivityGeneralTabBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        loadFragment(GeneralTabFragment())

    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcv_general_tab_fragment_container, fragment).commit()
    }


}