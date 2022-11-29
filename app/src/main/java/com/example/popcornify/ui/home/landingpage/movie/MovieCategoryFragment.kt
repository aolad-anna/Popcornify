package com.example.popcornify.ui.home.landingpage.movie

import AllMovies
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcornify.R
import com.example.popcornify.databinding.FragmentHomeBinding
import com.example.popcornify.databinding.FragmentMovieCategoryBinding
import com.example.popcornify.ui.adapter.RecyclerAdapterAllMovie
import com.example.popcornify.ui.api.ApiClient
import com.example.popcornify.ui.api.ApiInterface
import com.example.popcornify.ui.home.HomePageViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieCategoryFragment : Fragment() {

    private lateinit var homeViewModel: HomePageViewModel

    lateinit var recyclerViewAllMovie: RecyclerView
    lateinit var recyclerAdapterAllMovie: RecyclerAdapterAllMovie

    private var _binding: FragmentMovieCategoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomePageViewModel::class.java)

        _binding = FragmentMovieCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        val toolbar = activity?.findViewById<TextView>(R.id.message_tv)
        toolbar?.text = "Popcornify"

        recyclerViewAllMovie = binding.root.findViewById(R.id.card_re)
        recyclerAdapterAllMovie = RecyclerAdapterAllMovie(requireContext())
        recyclerViewAllMovie.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewAllMovie.adapter = recyclerAdapterAllMovie

        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<AllMovies>> = apiService.getAllMovies()
        call.enqueue(object : Callback<List<AllMovies>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<AllMovies>>, response: Response<List<AllMovies>>) {

                if (response != null) {
                    response.body()?.let { recyclerAdapterAllMovie.setAllMoviesListListItems(it) }
                    val controll = activity?.findViewById<View>(R.id.movieCardPlace)
                    controll?.visibility=View.GONE
                    _binding!!.movieCard.visibility=View.VISIBLE
                }
                else {
                    Toast.makeText(activity, "response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<List<AllMovies>>, t: Throwable) {
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