package com.battman.data.network.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class ProgressRequestBody(
    private val file: File,
    private val contentType: String,
    private val callback: (progress: Int) -> Unit
) : RequestBody() {

    override fun contentType(): MediaType? = contentType.toMediaTypeOrNull()

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
        val inputStream = FileInputStream(file)
        val buffer = ByteArray(BUFFER_SIZE)
        var uploaded: Long = 0
        val fileSize = file.length()

        try {
            while (true) {

                val read = inputStream.read(buffer)
                if (read == -1) break

                uploaded += read
                sink.write(buffer, 0, read)

                val progress = (((uploaded / fileSize.toDouble())) * 100).toInt()
                callback(progress)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }
    }

    companion object {
        const val BUFFER_SIZE = 1024
    }
}