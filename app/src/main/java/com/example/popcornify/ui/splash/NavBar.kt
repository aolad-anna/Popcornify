package com.example.popcornify.ui.splash


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.popcornify.R
import com.example.popcornify.databinding.ActivityNavBarBinding
import com.example.popcornify.ui.search.Search
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess


class NavBar : AppCompatActivity() {

    private lateinit var binding: ActivityNavBarBinding

    @SuppressLint("WrongViewCast", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_nav_bar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_movie,
                R.id.navigation_series,
                R.id.navigation_tv,
                R.id.navigation_menu
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val toolbar = findViewById<LottieAnimationView>(R.id.animationView)
        val imgUri: Uri = Uri.parse("https://lottie.host/4c933026-3e7f-459e-96bd-3ad49fb3236a/ozwcJcKfZk.json")
        toolbar.setAnimationFromUrl(imgUri.toString())

        val search = findViewById<View>(R.id.searchEn)
        search?.setOnClickListener {
            val i = Intent(baseContext, Search::class.java)
            startActivity(i)
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //***Change Here***
            intent.putExtra("EXIT", true);
            startActivity(intent)
            finish()
            exitProcess(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click Back again to EXIT App", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }

}