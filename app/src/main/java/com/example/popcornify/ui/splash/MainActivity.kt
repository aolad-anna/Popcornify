package com.example.popcornify.ui.splash


import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.popcornify.R
import com.example.popcornify.ui.api.InternetConnection


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val imageView: ImageView = findViewById<View>(R.id.pulseBeat) as ImageView
//        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse))

        if (InternetConnection.checkConnection(this@MainActivity)){
            Handler().postDelayed({
                val intent = Intent(baseContext, NavBar::class.java)
                startActivity(intent)
            }, 4000)
        }
        else{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity, R.style.DialogStyle).apply {
                setTitle("No internet or data connection!")
                setCancelable(false)
                setPositiveButton("Retry") { _, _ ->
                    try {
                        val i = Intent(baseContext, MainActivity::class.java)
                        startActivity(i)
                    }
                    catch (e: ActivityNotFoundException) {
                        throw e
                    }
                    this@MainActivity.finish()
                }
            }
            val alert = builder.create()
            alert.show()
            val updateButton: Button = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            updateButton.setTextColor(Color.parseColor("#FF9800"))
            updateButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, 34F)
        }
    }
}
