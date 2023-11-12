package com.example.data.remote.dto

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "image_front_thumb_url")
    val image: String,
    val nutriments: Nutriments,
    @field:Json(name = "product_name")
    val productName: String
)
