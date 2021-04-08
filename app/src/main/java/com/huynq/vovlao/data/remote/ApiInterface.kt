package com.vbeeon.iotdbs.data.remote

import com.huynq.vovlao.data.model.Chanel
import com.huynq.vovlao.data.model.User
import com.huynq.vovlao.data.remote.data.InitRequest
import com.vbeeon.iotdbs.data.model.ApiResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import java.nio.channels.Channel

interface ApiInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<User>

    @POST("/users")
    fun apiInit(@Body userDto: InitRequest): Single< ApiResult<User>>

    @GET("/chanel?")
    fun getChannels(@Query("userid") userid: String,
                @Query("languageid") languageid: Int): Single<ApiResult<List<Chanel>>>


//    @POST("api/v1/subscriber/login")
//    fun login(@Body loginRequest: LoginRequest): Single<ApiResult<LoginEntity>>
//
//    @POST("api/v1/supervisor/login")
//    fun loginSupervisor(@Body request: LoginSuperVisorRequest): Single<ApiResult<LoginSupervisorRemoteEntity>>

}