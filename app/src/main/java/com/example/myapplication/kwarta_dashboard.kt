package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale

class kwarta_dashboard : AppCompatActivity() {

    private lateinit var dashboardBalance : TextView
    private lateinit var dashboardUsername : TextView
    private lateinit var logoutBtn : ImageView;


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
        logoutBtn = findViewById(R.id.Logout);

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

        dashboardUsername.setText("Welcome ${username}");
        var displayBalance = balanceIntToString(balance);
        dashboardBalance.setText("${displayBalance}");


        //logout button clicked
        logoutBtn.setOnClickListener({
            val toLogin = Intent(this@kwarta_dashboard, MainActivity::class.java);
            startActivity(toLogin);
        })



    }



    fun balanceIntToString(balance: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        return formatter.format(balance.toDouble())
    }
}