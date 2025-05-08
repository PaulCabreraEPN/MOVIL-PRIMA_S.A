package com.example.primasaapp_mvil.model

data class ProductResponse(
    val status: String,
    val code: String,
    val msg: String,
    val data: List<Product>,
    val info: PageInfo
)

data class Product(
    val id: Int,
    val reference: String,
    val product_name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val imgUrl: String
)

data class PageInfo(
    val currentPage: Int,
    val totalPages: Int,
    val totalProducts: Int,
    val limit: Int
)
