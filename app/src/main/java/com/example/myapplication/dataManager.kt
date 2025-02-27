package com.example.myapplication
import android.util.Log

object DataManager {
    data class User(val username: String, val email:String ,val password: String, var balance: Int)
    val users = mutableListOf<User>()

    //MAIN FUNCTIONS
    fun addUser(username: String, email:String, password: String, balance: Int){
        val newUser = User(username,email, password, balance);
        users.add(newUser);
        Log.i("System log", "added user ${username} to database");
    }

    fun findUser(username: String, password: String): User? {
        var userAccount = users.find { it.username == username && it.password == password }
        return userAccount; //returns null if account not found
    }

    fun addBalance(amount: Int, username: String){
        var account = users.find{it.username == username}
        if(account!=null){
            account.balance += amount;
            Log.i("System Log", "Added ${amount} to user ${username} ")
        }
    }

    fun deductBalance(amount: Int, username: String){       //Note to self: make sure to verify user balance before using this func
        var account = users.find{it.username == username}
        if(account!=null){
            account.balance -= amount;
            Log.i("System Log", "Deducted ${amount} to user ${username} ")
        }
    }


    //FUNCTIONS FOR SIGNUP VERIFICATION
    //username Flag
    fun usernameExists(username: String):Boolean{
        return (users.find{it.username == username} != null)
    }

    //email Flag
    fun emailExists(email: String): Boolean{
        return (users.find{it.email == email} != null)
    }




}