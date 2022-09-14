package com.astroenvision.astropower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.astroenvision.astropower.database.UserDatabase
import com.astroenvision.astropower.entities.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = UserDatabase.getDatabaseInstance(this)

        GlobalScope.launch {
            database.userDao().insertUser(
                UserEntity(0, "Prahlad", "9785852630", "singhprahlad366@gmail.com", "My Photo")
            )
        }

        Log.d("Table","Data Inserted")

    }
}