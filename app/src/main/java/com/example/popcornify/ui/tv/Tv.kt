package com.example.popcornify.ui.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.popcornify.R
import com.example.popcornify.ui.search.Search
import com.example.popcornify.ui.splash.NavBar

class Tv : Fragment() {

    companion object {
        fun newInstance() = Tv()
    }

    private lateinit var viewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(TvViewModel::class.java)
        val toolbar = activity?.findViewById<TextView>(R.id.message_tv)
        toolbar?.text = "TV Channels"

        val lottieJs = activity?.findViewById<LottieAnimationView>(R.id.animationView)
//        val imgUri: Uri = Uri.parse("https://lottie.host/fb06f34e-c2d5-43f7-a228-10124bb989e1/cK9bFGH1nP.json")
//        lottieJs?.setAnimationFromUrl(imgUri.toString())
//        lottieJs?.loop(true)
        lottieJs?.setOnClickListener{
            findNavController().navigate(R.id.navigation_home)
        }
    }

}