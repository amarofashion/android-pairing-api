package com.amaro.service

import com.amaro.model.GuideShop

interface GuideShopService {
    suspend fun list(): List<GuideShop?>
    suspend fun create(newGuideShop: GuideShop?): GuideShop?
    suspend fun update(updatedGuideShop: GuideShop?): GuideShop?
    suspend fun get(id: Int?): GuideShop?
    suspend fun delete(id: Int?)
}