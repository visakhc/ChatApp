package com.visakh.demochatapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.visakh.demochatapp.R
import com.visakh.demochatapp.utils.shortToast
import io.socket.client.IO
import io.socket.client.Socket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}