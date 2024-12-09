package com.battman.core.contentresolver.impl

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.battman.core.contentresolver.api.IContentResolverHelper
import java.io.File
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(
    private val contentResolver: ContentResolver,
) : IContentResolverHelper {

    override fun uriToFile(context: Context, uri: Uri): File? {
        return when (uri.scheme) {
            "file" -> uri.path?.let { File(it) }
            "content" -> {
                val file = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}.tmp")
                try {
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        file.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                file
            }
            else -> null
        }
    }

    override fun getFileName(uri: Uri): String? {
        val returnCursor = contentResolver.query(uri, null, null, null, null) ?: return null
        val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val fileName = returnCursor.getString(nameIndex)
        returnCursor.close()
        return fileName
    }
}
