package com.example.myapplication

import android.accounts.Account
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
            Log.i("System Log Dashboard", "onCreate: cash in button")
        }

        cashOutButton.setOnClickListener{
            DataManager.deductBalance(100,username)
            updateBalanceDisplay()
            Log.i("System Log Dashboard", "onCreate: cash out button")
        }



        //logout button clicked
        logoutBtn.setOnClickListener{
            val intent = Intent(this@kwarta_dashboard, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }


        //Double press back button to logout
        var backButtonPressCounter = 0
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backButtonPressCounter > 0) {
                    val intent = Intent(this@kwarta_dashboard, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Press again to Logout", Toast.LENGTH_SHORT).show()
                    backButtonPressCounter++

                    // Reset counter after 2 seconds
                    Handler(Looper.getMainLooper()).postDelayed({
                        backButtonPressCounter = 0
                    }, 2000)
                }
            }
        })



    }

    fun updateBalanceDisplay(){
        balance = account?.balance ?: 0
        val displayBalance = balanceIntToString(balance)
        dashboardBalance.text = displayBalance
        balance = account?.balance ?: 0
        Log.i("System Log Dashboard", "updateBalanceDisplay: $balance")

    }

    fun balanceIntToString(balance: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        return formatter.format(balance.toDouble())
    }


}