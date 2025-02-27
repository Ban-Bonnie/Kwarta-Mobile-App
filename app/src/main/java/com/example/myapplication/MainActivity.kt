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
import com.example.myapplication.DataManager


class MainActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        DataManager.addUser("Bonnie","bonniegwapo123@gmail.com","bonnie123",10000);
        DataManager.addUser("Nonoy812","nonoy123@gmail.com","nonoy123",500);

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.login_button)
        signupBtn = findViewById(R.id.signup_button)


        // Set up the login button click listener
        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString();
            val password = passwordInput.text.toString();

            val account = DataManager.findUser(username,password);
            if(account!= null){
                //Logged in
                val toDashboard = Intent(this@MainActivity, kwarta_dashboard::class.java)
                toDashboard.putExtra("username", username);
                toDashboard.putExtra("password", password);
                Log.i("System Log", "user ${username} logged in ");
                startActivity(toDashboard)

            }
            else{
                //login Failed
                Toast.makeText(applicationContext,"Incorrect Username or Password",Toast.LENGTH_LONG).show();
            }
        }//Login Listener


        //to Signup activity
        signupBtn.setOnClickListener{
            val toSignup = Intent(this@MainActivity, signup::class.java);
            startActivity(toSignup);
        }

        //Disable back button -gpt coded
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })


    }







}