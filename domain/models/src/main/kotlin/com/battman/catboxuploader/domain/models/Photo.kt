package com.battman.catboxuploader.domain.models

import java.util.Date

data class Photo(
    val id: Long,
    val name: String,
    val contentUri: String,
    val data: Date
)