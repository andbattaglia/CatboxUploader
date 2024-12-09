package com.battman.catboxuploader.photos.impl.datasource

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CatboxApiContract {

    @Multipart
    @POST("user/api.php")
    suspend fun createAlbum(
        @Part("reqtype") reqType: RequestBody,
        @Part("userhash") userHash: RequestBody,
        @Part fileToUpload: MultipartBody.Part,
    ): Response<ResponseBody>
}
