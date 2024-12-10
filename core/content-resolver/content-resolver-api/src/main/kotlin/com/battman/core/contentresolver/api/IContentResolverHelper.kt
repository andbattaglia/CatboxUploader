package com.battman.core.contentresolver.api

import android.net.Uri
import java.io.File

interface IContentResolverHelper {

    fun uriToFile(uri: Uri): File?

    fun getFileName(uri: Uri): String?
}
