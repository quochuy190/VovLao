package com.vbeeon.iotdbs.data.remote

import com.huynq.vovlao.data.model.*
import com.huynq.vovlao.data.remote.data.InitRequest
import com.vbeeon.iotdbs.data.model.ApiResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<User>

    @POST("/users")
    fun apiInit(@Body userDto: InitRequest): Single<ApiResult<List<User>>>

    @GET("/channel?")
    fun getChannels(
        @Query("userId") userid: String,
        @Query("languageId") languageid: Int
    ): Single<ApiResult<List<Chanel>>>

    @GET("/program?")
    fun getProgram(
        @Query("userId") userid: String,
        @Query("languageId") languageid: Int,
        @Query("channelId") channelId: Int
    ): Single<ApiResult<List<Epg>>>

    @GET("/newsCategory?")
    fun getNewsCategory(
        @Query("userId") userid: String,
        @Query("languageId") languageid: Int
    ): Single<ApiResult<List<NewCategory>>>

    @GET("/newsCategory/listNews?")
    fun getNewsByCategory(
        @Query("userId") userid: String,
        @Query("categoryId") categoryId: Int,
        @Query("languageId") languageid: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<ApiResult<List<News>>>

    @GET("/newsDetail?")
    fun getNewsDetail(
        @Query("userId") userid: String,
        @Query("languageId") languageid: Int,
        @Query("newsId") newsId: Int,
        @Query("newsCategoryId") newsCategoryId: Int
    ): Single<ApiResult<List<News>>>


//    @POST("api/v1/subscriber/login")
//    fun login(@Body loginRequest: LoginRequest): Single<ApiResult<LoginEntity>>
//
//    @POST("api/v1/supervisor/login")
//    fun loginSupervisor(@Body request: LoginSuperVisorRequest): Single<ApiResult<LoginSupervisorRemoteEntity>>

}