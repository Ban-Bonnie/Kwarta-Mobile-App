package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class kwarta_dashboard : AppCompatActivity() {

    private lateinit var dashboardBalance : TextView
    private lateinit var dashboardUsername : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kwarta_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        dashboardBalance = findViewById(R.id.accountBalance);
        dashboardUsername = findViewById(R.id.username);

        //fetching extra values;
        var username = intent.getStringExtra("username");
        var password = intent.getStringExtra("password");
        var balance = 0;
        var email = "email@gmail.com"

        if ((username != null)&&(password != null)) {
            var account =  DataManager.findUser(username,password)
            username = account?.username;
            password = account?.password;
            balance = account?.balance!!;

        }

        dashboardUsername.setText("Welcome ${username}")
        dashboardBalance.setText("${balance}")






    }
}