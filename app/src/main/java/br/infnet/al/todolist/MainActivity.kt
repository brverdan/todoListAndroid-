package br.infnet.al.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        buttonNavigationView.visibility = BottomNavigationView.GONE
        buttonNavigationView.setupWithNavController(findNavController(R.id.navHostFragment))
    }
}