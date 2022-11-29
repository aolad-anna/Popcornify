package com.example.popcornify.ui.home.landingpage.carousel

import Carousel
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popcornify.R
import com.example.popcornify.databinding.FragmentCarouselBinding
import com.example.popcornify.ui.adapter.RecyclerAdapterCarousel
import com.example.popcornify.ui.api.ApiClient
import com.example.popcornify.ui.api.ApiInterface
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarouselFragment : Fragment() {

    private lateinit var carouselViewModel: CarouselViewModel

    lateinit var carouselRecyclerview: RecyclerView
    lateinit var carouselRecyclerAdapter: RecyclerAdapterCarousel

    private var _binding: FragmentCarouselBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        carouselViewModel =
            ViewModelProvider(this)[CarouselViewModel::class.java]

        _binding = FragmentCarouselBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val toolbar = activity?.findViewById<TextView>(R.id.message_tv)
        toolbar?.text = "Popcornify"

        val carouselRecyclerview = binding.root.findViewById<CarouselRecyclerview>(R.id.recycler)
        carouselRecyclerAdapter = RecyclerAdapterCarousel(requireContext())
        carouselRecyclerview.adapter = carouselRecyclerAdapter
        carouselRecyclerview.set3DItem(true)
        carouselRecyclerview.setAlpha(true)
        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        val currentlyCenterPosition = carouselRecyclerview.getSelectedPosition()

        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<Carousel>> = apiService.getCarousel()
        call.enqueue(object : Callback<List<Carousel>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Carousel>>, response: Response<List<Carousel>>) {

                if (response != null) {
                    response.body()?.let { carouselRecyclerAdapter.setCarouselListItems(it)}
                    val controll = activity?.findViewById<View>(R.id.carouselCardPlace)
                    controll?.visibility=View.GONE
                    _binding!!.carouselCard.visibility=View.VISIBLE
                    var x = 0
                    if (x == 0){
                        Handler().postDelayed({carouselLayoutManager.scrollToPosition(x)}, 3000)
                        x++
                        Log.i("LOG","Print: $x")
                    }
                }
                else {
                    Toast.makeText(activity, "response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<List<Carousel>>, t: Throwable) {
                if (call.isCanceled) {
                    Toast.makeText(context, "Response is Canceled", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(activity, "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        })

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}