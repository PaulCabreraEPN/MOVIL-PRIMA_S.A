package com.example.primasaapp_mvil.model

data class ProductResponse(
    val status: String,
    val code: String,
    val msg: String,
    val data: List<Product>,
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

data class ProductToSend(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
)


