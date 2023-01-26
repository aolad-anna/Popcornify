package com.example.popcornify.ui.home.landingpage.carousel

import Carousel
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
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
    private lateinit var carouselRecyclerview: RecyclerView
    private lateinit var carouselRecyclerAdapter: RecyclerAdapterCarousel

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

        carouselRecyclerview = binding.root.findViewById<CarouselRecyclerview>(R.id.recycler)
        carouselRecyclerAdapter = RecyclerAdapterCarousel(requireContext())
        carouselRecyclerview.adapter = carouselRecyclerAdapter
        (carouselRecyclerview as CarouselRecyclerview?)?.set3DItem(true)
        (carouselRecyclerview as CarouselRecyclerview?)?.setAlpha(true)
        val carouselLayoutManager = (carouselRecyclerview as CarouselRecyclerview?)?.getCarouselLayoutManager()
        val currentlyCenterPosition = (carouselRecyclerview as CarouselRecyclerview?)?.getSelectedPosition()

        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<Carousel>> = apiService.getCarousel()
        call.enqueue(object : Callback<List<Carousel>> {
            override fun onResponse(call: Call<List<Carousel>>, response: Response<List<Carousel>>) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { carouselRecyclerAdapter.setCarouselListItems(it)}
                        val placeHolder = activity?.findViewById<View>(R.id.carouselCardPlace)
                        placeHolder?.visibility=View.GONE
                        _binding!!.carouselCard.visibility=View.VISIBLE
                        autoScrollCarousel()
                    }
                    else
                        Toast.makeText(context, "Something went to wrong!!", Toast.LENGTH_LONG).show()
                }
                catch (e: Exception) {
                    throw e
                }
            }

            override fun onFailure(call: Call<List<Carousel>>, t: Throwable) {
                if (call.isCanceled) {
                    t.printStackTrace()
                }
                else {
                    t.printStackTrace()
                }
            }
        })
        return root
    }

    private fun autoScrollCarousel() {
        val speedScroll = 3500
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            var count = 0
            var flag = true
            override fun run() {
                if (count < carouselRecyclerAdapter.itemCount) {
                    if (count == carouselRecyclerAdapter.itemCount - 1) {
                        flag = false
                    } else if (count == 0) {
                        flag = true
                    }
                    if (flag) count++ else count--
                    carouselRecyclerview.smoothScrollToPosition(count)
                    handler.postDelayed(this, speedScroll.toLong())
                }
            }
        }
        handler.postDelayed(runnable, speedScroll.toLong())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}