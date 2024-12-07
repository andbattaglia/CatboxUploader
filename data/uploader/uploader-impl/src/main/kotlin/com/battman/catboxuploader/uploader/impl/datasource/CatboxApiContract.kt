package com.battman.catboxuploader.uploader.impl.datasource

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CatboxApiContract {

    @Multipart
    @POST("user/api.php")
    suspend fun createAlbum(
        @Part("reqtype") reqType: RequestBody,
        @Part("userhash") userHash: RequestBody,
        @Part fileToUpload: MultipartBody.Part
    ): Either<CallError, String>
}
