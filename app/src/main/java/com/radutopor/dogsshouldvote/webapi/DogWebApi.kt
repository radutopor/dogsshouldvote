package com.radutopor.dogsshouldvote.webapi

import com.radutopor.dogsshouldvote.webapi.models.BreedsApiResp
import com.radutopor.dogsshouldvote.webapi.models.ImagesApiResp
import retrofit2.http.GET
import retrofit2.http.Path

interface DogWebApi {
    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedsApiResp

    @GET("breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): ImagesApiResp

    @GET("breed/{breed}/{subBreed}/images")
    suspend fun getSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String,
    ): ImagesApiResp
}