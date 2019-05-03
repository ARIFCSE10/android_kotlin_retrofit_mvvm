package com.beliefit.tmq.mybookshop.Interface

import com.beliefit.tmq.mybookshop.model.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpointInterface {
//    Request method and URL specified in the annotation

    @POST("/login")
    fun doLogin(@Body userLogin: UserLogin): Observable<UserLoginResponse>


    @POST("/signup")
    fun doSignup(@Body userRegister: UserRegister): Observable<UserRegisterResponse>

    @GET("/books")
    fun doFetchBookList(): Observable<BookListResponse>
//
//    @GET("events/event/")
//    fun getEvent(): Observable<EventResponse>

}