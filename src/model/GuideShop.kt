package com.amaro.model

import com.amaro.database.GuideShopsTable
import org.jetbrains.exposed.sql.ResultRow

data class GuideShop(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)

fun ResultRow.toGuideShop(): GuideShop {
    return GuideShop(
        id = this[GuideShopsTable.id],
        name = this[GuideShopsTable.name],
        address = this[GuideShopsTable.address],
        latitude = this[GuideShopsTable.latitude],
        longitude = this[GuideShopsTable.longitude]
    )
}