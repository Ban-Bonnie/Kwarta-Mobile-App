package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class signup : AppCompatActivity() {
    private lateinit var toLogin:TextView;
    private lateinit var signupBtn: Button

    private lateinit var userName: EditText;
    private lateinit var userEmail: EditText;
    private lateinit var userPassword: EditText;
    private lateinit var userConfirmPassword: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        userName = findViewById(R.id.nameInput);
        userEmail = findViewById(R.id.emailInput);
        userPassword = findViewById(R.id.passwordInput);
        userConfirmPassword = findViewById(R.id.confirmPasswordInput);

        signupBtn = findViewById(R.id.signupButton)
        signupBtn.setOnClickListener{
            val userName = userName.text.toString();
            val userEmail = userEmail.text.toString();
            val userPassword = userPassword.text.toString();
            val userConfirmPassword = userConfirmPassword.text.toString();



            if(validateSignupDetails(userName, userEmail, userPassword,userConfirmPassword)){
                //Valid user details
                Log.i("System Log", "Successfully signed in as ${userName}")
                DataManager.addUser(userName,userEmail,userPassword, 0);

                val toLogin = Intent(this@signup, MainActivity::class.java);
                startActivity(toLogin);
                Toast.makeText(applicationContext,"Signed in Successfully", Toast.LENGTH_LONG).show();

            }
            else{
                Log.i("System Log", "user invalid signup details ")
            }

        }

        //back to Login
        toLogin = findViewById(R.id.toLogin)
        toLogin.setOnClickListener{
            val toLogin = Intent(this@signup, MainActivity::class.java);
            startActivity(toLogin);
        }

    }



    private fun validateSignupDetails(username: String, email: String, password: String, confirmPassword:String): Boolean{

        //Username
        if ((DataManager.usernameExists(username))) {
            Toast.makeText(applicationContext,"username ${username} is already taken", Toast.LENGTH_LONG).show();
            return false; //username taken
        }

        //Email
        if(DataManager.emailExists(email)){
            Toast.makeText(applicationContext,"email already taken", Toast.LENGTH_LONG).show();
            return false; //email taken
        }

        //Password
        if(!(password.equals(confirmPassword))){
            Toast.makeText(applicationContext,"Password does not match", Toast.LENGTH_LONG).show();
            return false; //password does not match with
        }
        if(password.length < 7){
            Toast.makeText(applicationContext,"Password must be at least 8 characters long", Toast.LENGTH_LONG).show();
            return false; //password must be 8 characters
        }

        return true;
    }

}