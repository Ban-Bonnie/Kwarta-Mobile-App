package com.example.myapplication

import android.accounts.Account
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Button
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

    private lateinit var cashInButton : Button;
    private lateinit var cashOutButton : Button

    lateinit var username:String;
    lateinit var password:String;
    var balance : Int = 0;

    var account: DataManager.User? = null



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
        cashInButton = findViewById(R.id.cashInButton);
        cashOutButton = findViewById(R.id.cashOutButton);

        //fetching extra values;
        username = intent.getStringExtra("username") ?: "Guest";
        password = intent.getStringExtra("password") ?: "Guest";
        var email = "email@gmail.com"


        // Fetching account from DataManager
        account = DataManager.findUser(username, password)
        username = account?.username ?: "User not found"
        password = account?.password ?: "User not found"
        balance = account?.balance ?: 0



        dashboardUsername.text = "Welcome ${username}"
        updateBalanceDisplay()

        //cash in button
        cashInButton.setOnClickListener{
            DataManager.addBalance(100,username)
            updateBalanceDisplay()
        }

        cashOutButton.setOnClickListener{
            DataManager.deductBalance(100,username)
            updateBalanceDisplay()
        }



        //logout button clicked
        logoutBtn.setOnClickListener{
            val toLogin = Intent(this@kwarta_dashboard, MainActivity::class.java);
            startActivity(toLogin);
        }




    }

    fun updateBalanceDisplay(){
        val displayBalance = balanceIntToString(balance)
        dashboardBalance.text = displayBalance
        balance = account?.balance ?: 0

    }

    fun balanceIntToString(balance: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        return formatter.format(balance.toDouble())
    }


}