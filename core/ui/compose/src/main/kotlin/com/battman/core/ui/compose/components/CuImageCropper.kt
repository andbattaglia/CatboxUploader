package com.battman.core.ui.compose.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions

@Composable
fun ImageCropper(
    uri: Uri,
    onCropSuccess: (Uri) -> Unit,
    onCropFailure: (Throwable) -> Unit,
) {
    val imageCropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            if (result.isSuccessful) {
                onCropSuccess(result.uriContent!!)
            } else {
                val exception = result.error
                onCropFailure(exception!!)
            }
        },
    )

    LaunchedEffect(Unit) {
        val cropOptions = CropImageContractOptions(uri, CropImageOptions())
        imageCropLauncher.launch(cropOptions)
    }
}
