package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET

interface MessagesApi {

    @GET("/v3/744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getMessages(): Response<List<MessagesItem>>

}