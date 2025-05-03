package com.example.primasaapp_mvil.model

data class OrdersResponse(
    val data: List<Order>
)

data class Order(
    val customer: CustomerData,
    val products: List<ProductSelected>,
    val discountApplied: Double,
    val netTotal: Double,
    val totalWithTax: Double,
    val status: String,
    val comment: String,
    val seller: SellerData
)

data class CustomerData(
    val _id: String,
    val Name: String,
    val Ruc: Long,
    val Address: String,
    val telephone: Long,
    val email: String,
    val credit: String,
    val state: String
)

data class ProductSelected(
    val productId: String,
    val quantity: Int,
    val productDetails: ProductDetails
)

data class ProductDetails(
    val _id: String,
    val product_name: String,
    val measure: String,
    val price: Double
)

data class SellerData(
    val _id: String,
    val names: String,
    val lastNames: String,
    val numberID: Long,
    val email: String,
    val SalesCity: String,
    val PhoneNumber: Long,
)