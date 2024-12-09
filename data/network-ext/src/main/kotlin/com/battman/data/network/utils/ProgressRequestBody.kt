package com.battman.data.network.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class ProgressRequestBody(
    private val file: File,
    private val contentType: String
) : RequestBody() {

    /**
     * Provides a `Flow` of progress updates during the file write operation.
     *
     * @return A `Flow` emitting progress as an integer percentage (0-100).
     */
    fun progressFlow(): Flow<Int> = flow {
        val inputStream = FileInputStream(file)
        val buffer = ByteArray(BUFFER_SIZE)
        var uploaded: Long = 0
        val fileSize = file.length()

        try {
            while (true) {
                val read = inputStream.read(buffer)
                if (read == -1) break

                uploaded += read
                emit(((uploaded / fileSize.toDouble()) * 100).toInt())
            }
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream.close()
        }
    }

    override fun contentType(): MediaType? = contentType.toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        val inputStream = FileInputStream(file)
        val buffer = ByteArray(BUFFER_SIZE)

        try {
            while (true) {
                val read = inputStream.read(buffer)
                if (read == -1) break
                sink.write(buffer, 0, read)
            }
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream.close()
        }
    }

    companion object {
        const val BUFFER_SIZE = 4096
    }
}