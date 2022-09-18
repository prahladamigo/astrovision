package com.astroenvision.astropower.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.astroenvision.astropower.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    //lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       /* database = UserDatabase.getDatabaseInstance(this)

        GlobalScope.launch {
            database.userDao().insertUser(
                UserEntity(0, "Prahlad", "9785852630", "singhprahlad366@gmail.com", "My Photo")
            )
        }*/


    }
}