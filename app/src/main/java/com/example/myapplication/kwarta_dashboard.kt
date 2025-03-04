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
import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout

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
            showCashInDialog(this)
        }

        cashOutButton.setOnClickListener{
           showCashOutDialog(this)
        }



        //logout button clicked
        logoutBtn.setOnClickListener{
            showLogoutConfirmationDialog(this)


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

    fun showCashInDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Cash In Amount")

        // Create an EditText for user input
        val input = EditText(context)
        input.hint = "Enter amount"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        // Set layout parameters for better spacing
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)
        layout.addView(input)

        builder.setView(layout)

        // Handle user actions
        builder.setPositiveButton("OK") { dialog, _ ->
            val amount = input.text.toString()
            if (amount.isNotEmpty()) {
                Toast.makeText(context, "successfully cashed in $$amount", Toast.LENGTH_SHORT).show()
                DataManager.addBalance(amount.toInt(),username)
                updateBalanceDisplay()
                Log.i("System Log Dashboard", "onCreate: cash in button")

            } else {
                Toast.makeText(context, "Please enter an amount!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        // Show the dialog
        builder.show()
    }
    fun showCashOutDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Cash Out Amount")

        // Create an EditText for user input
        val input = EditText(context)
        input.hint = "Enter amount"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        // Set layout parameters for better spacing
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)
        layout.addView(input)

        builder.setView(layout)

        // Handle user actions
        builder.setPositiveButton("OK") { dialog, _ ->
            val amount = input.text.toString()
            if (amount.toInt()> account!!.balance){
                Toast.makeText(context, "Not enough balance", Toast.LENGTH_SHORT).show()
            }
            else if (amount.isNotEmpty()) {

                Toast.makeText(context, "successfully cashed out $$amount", Toast.LENGTH_SHORT).show()
                DataManager.deductBalance(amount.toInt(),username)
                updateBalanceDisplay()
                Log.i("System Log Dashboard", "onCreate: cash out button")

            } else {
                Toast.makeText(context, "Please enter an amount!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        // Show the dialog
        builder.show()
    }

    fun showLogoutConfirmationDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            val intent = Intent(this@kwarta_dashboard, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }


}