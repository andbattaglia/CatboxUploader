package com.battman.core.contentresolver.api

import android.content.Context
import android.net.Uri
import java.io.File

interface IContentResolverHelper {

    fun uriToFile(context: Context, uri: Uri): File?

    fun getFileName(uri: Uri): String?
}
