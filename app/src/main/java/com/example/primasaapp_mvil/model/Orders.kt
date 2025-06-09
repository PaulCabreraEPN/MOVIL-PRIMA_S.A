package com.example.primasaapp_mvil.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class OrdersResponse(
    val data: List<Order>
)

data class Order(
    val _id: String,
    val customer: CustomerData,
    val products: List<ProductSelected>,
    val discountApplied: Double,
    val netTotal: Double,
    val totalWithTax: Double,
    val status: String,
    val credit: String,
    val comment: String,
    val seller: SellerData,
    val registrationDate: String?,
    val lastUpdate: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val __v: Int?
)


data class CustomerData(
    val _id: String,
    val Name: String,
    val ComercialName: String,
    val Ruc: String,
    val Address: String,
    val telephone: String,
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
    val id: Int,
    val product_name: String?,
    val reference: String?,  // antes era String
    val price: Double?
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

data class OrderToSend(
    val customer: String,
    val products: List<ProductToSendJSON>,
    val discountApplied: Int,
    val netTotal: BigDecimal?,
    val totalWithTax: BigDecimal?,
    val comment: String,
    val credit: String,
)

data class ProductToSendJSON(
    @SerializedName("productId") val id: String,
    val quantity: Int
)

data class OrderResponsetoSend(
    val status: String,
    val code: String,
    val msg: String,
    val data: OrderData,
    val info: OrderInfo
)

data class OrderResponsetoID(
    val status: String,
    val code: String,
    val msg: String,
    val data: OrderDatabyID,
)

data class OrderDatabyID(
    val _id: String,
    val customer: CustomerData,
    val products: List<ProductSelected>,
    val discountApplied: Int,
    val netTotal: Double,
    val totalWithTax: Double,
    val credit: String,
    val status: String,
    val comment: String,
    val registrationDate: String,
    val seller: Seller,
    val lastUpdate: String?,
    val createdAt: String?,
    val updatedAt: String?,
)

data class OrderData(
    val _id: String,
    val customer: Long,
    val products: List<ProductItem>,
    val discountApplied: Int,
    val netTotal: Double,
    val totalWithTax: Double,
    val credit: String,
    val status: String,
    val comment: String,
    val registrationDate: String,
    val lastUpdate: String,
    val seller: String
)

data class ProductItem(
    val productId: String,
    val quantity: Int
)

data class OrderInfo(
    val stockUpdateDetails: StockUpdateDetails
)

data class StockUpdateDetails(
    val attempted: Int,
    val modified: Int,
    val status: String,
    val message: String
)
