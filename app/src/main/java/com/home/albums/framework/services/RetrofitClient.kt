package com.home.albums.framework.services

import com.home.albums.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

val appClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

val albumService: AlbumService by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(appClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(AlbumService::class.java)
}
