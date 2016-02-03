package com.aldoborrero.muzei.ch.api

import com.aldoborrero.muzei.ch.api.model.Photo
import retrofit.Call
import retrofit.http.GET

interface CalvinHobbesApiService {

    @GET("v1/")
    public fun randomPhoto(): Call<Photo>
}
