package com.example.popcornify.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.popcornify.R
import kotlinx.coroutines.delay


class Home : Fragment() {

    companion object {
        fun newInstance() = Home()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val toolbar = activity?.findViewById<TextView>(R.id.message_tv)
        toolbar?.text = "Popcornify"

        val lottieJs = activity?.findViewById<LottieAnimationView>(R.id.animationView)
//        val imgUri: Uri = Uri.parse("https://lottie.host/fb06f34e-c2d5-43f7-a228-10124bb989e1/cK9bFGH1nP.json")
//        lottieJs?.setAnimationFromUrl(imgUri.toString())
//        lottieJs?.loop(true)
        lottieJs?.setOnClickListener{
            findNavController().navigate(R.id.navigation_home)
        }
    }
}