package com.battman.catboxuploader.domain.models

sealed class UploadDigest {
    data class Progress(
        val index: Int,
        val total: Int,
        val progress: Int,
    ) : UploadDigest()

    data object Success: UploadDigest()

    data class Error(
        val index: Int,
        val total: Int,
        val errorType: ErrorType,
    ) : UploadDigest()
}