package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button

    val usernameList = listOf("Bonnie", "Nonoy")
    val passwordList = listOf("bonnie123", "nonoy812")
    val emailList = listOf("bonniegwapo@gmail.com","ihaveADHD@gmail.com");
    val balanceList = listOf(2000,300)
    var userIndex = 0;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.login_button)
        signupBtn = findViewById(R.id.signup_button)
        userIndex = 0;


        // Set up the login button click listener
        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if(verifyLogin(username, password)){
                //Logged in
                val toDashboard = Intent(this@MainActivity, kwarta_dashboard::class.java)
                toDashboard.putExtra("balance", balanceList.get(userIndex))
                Log.i("logindetails", "user index: ${userIndex} user balance: ${balanceList.get(userIndex)}");
                startActivity(toDashboard)

            }
            else{
                //login failed
            }
        }//Login Listener


        //to Signup activity
        signupBtn.setOnClickListener{
            val toSignup = Intent(this@MainActivity, signup::class.java)
            toSignup.putStringArrayListExtra("usernames", ArrayList(usernameList));
            toSignup.putStringArrayListExtra("emails", ArrayList(emailList));
            startActivity(toSignup)
        }

        //Disable back button -gpt coded
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })


    }

    //Authenticate user login
    private fun verifyLogin(username: String, password: String): Boolean {
        if (username in this.usernameList && password == this.passwordList[this.usernameList.indexOf(username)]) {
            Log.i("Login Status", "Username found: $username and password: $password")

            Toast.makeText(applicationContext,"User $username logged in successfully", Toast.LENGTH_LONG).show();
            userIndex = this.usernameList.indexOf(username);
            return(true);



        } else {
            Log.i("Login Status", "Not found: $username")
            Toast.makeText(applicationContext, "Incorrect username or password",Toast.LENGTH_LONG).show();
            return (false);

        }
    }






}