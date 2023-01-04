package com.example.bottomnavigation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    private lateinit var frame:FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frame=findViewById(R.id.frame)
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener  {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                }
                R.id.message -> {
                    loadFragment(MenuFragment())
                }
                R.id.settings -> {
                    loadFragment(SettingsFragment())
                }

            }
            return@setOnItemSelectedListener true
        }
    }
    private  fun loadFragment(fragment:androidx.fragment.app.Fragment){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}