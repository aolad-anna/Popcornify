package com.example.popcornify.ui.home


import AllMovies
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.popcornify.R
import com.example.popcornify.databinding.FragmentHomeBinding



class HomePageFragment : Fragment() {

    private lateinit var homeViewModel: HomePageViewModel

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomePageViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        val toolbar = view?.findViewById<TextView>(R.id.message_tv)
        toolbar?.text = "Popcornify"

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}