package com.example.lab5_quizapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.lab5_quizapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutDrawer:DrawerLayout = findViewById(R.id.drawerLayout)
        image_menu.setOnClickListener { layoutDrawer.openDrawer(GravityCompat.START) }

        navigationView.itemIconTintList = null

        var navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(navigationView,navController)
    }
}