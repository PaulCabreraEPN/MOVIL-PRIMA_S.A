package com.example.primasaapp_mvil.model

data class ProductsResponse(
    val data : List<Product>
)

data class Product(
    val id : String,
    val product_name: String,
    val measure: String,
    val price: Double,
    val stock: Int,
    val imgUrl: String
)