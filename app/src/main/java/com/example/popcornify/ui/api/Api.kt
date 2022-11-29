package com.example.popcornify.ui.api


import AllMovies
import Carousel
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {

    @GET(CAROUSEL_POINT)
    fun getCarousel() : Call<List<Carousel>>

    @GET(MOVIE_POINT)
    fun getAllMovies() : Call<List<AllMovies>>
//
//    @GET("movies.php")
//    fun getDay() : Call<List<Day>>
//
//    @GET("movies1.php")
//    fun getAllMovies1() : Call<List<AllMovies1>>
//
//    @GET("carousel.php")
//    fun getCarousel() : Call<List<Carousel>>
//
//    @GET("tv.php")
//    fun getTv() : Call<List<Tv>>
//
//    @GET("tv1.php")
//    fun getTv1() : Call<List<Tv1>>
//
//    @GET("tv2.php")
//    fun getTv2() : Call<List<Tv2>>
//
//    @GET("tv3.php")
//    fun getTv3() : Call<List<Tv3>>
//
//    @GET("hindi_movie.php")
//    fun getHindiMovie() : Call<List<HindiMovie>>
//
//    @GET("bangla_movie.php")
//    fun getBanglaMovie() : Call<List<BanglaMovie>>
//
//    @GET("tamil_movie.php")
//    fun getTamilMovie() : Call<List<TamilMovie>>
//
//    @GET("category.php")
//    fun getCategory() : Call<List<Category>>
//
//
//    @GET("movies.php")
//    fun getMovies() : Call<List<Movie>>
//
//    @GET("movies.php")
//    fun getParty() : Call<List<Party>>
//
//    @GET("movies.php")
//    fun getLang() : Call<List<Lang>>
//
//    @GET("movies.php")
//    fun getGenres() : Call<List<Gen>>
//
//    @GET("movies.php")
//    fun getErass() : Call<List<Eraa>>
//
//
//    @GET("login.php")
//    fun getLogin(
//        @Query("email") email: String?,
//        @Query("password") password: String?
//    ): Call<Login>
//
//    @POST("reg_api.php")
//    fun getSignup(
//        @Query("name") name: String?,
//        @Query("email") email: String?,
//        @Query("phone") phone: String?,
//        @Query("password") password: String?
//    ): Call<Signup>
}