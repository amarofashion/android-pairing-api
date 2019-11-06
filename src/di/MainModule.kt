package com.amaro.di

import com.amaro.controller.GuideShopController
import com.amaro.database.dbConnect
import com.amaro.service.GuideShopService
import com.amaro.service.GuideShopServiceImpl
import com.google.inject.AbstractModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        application.install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        application.install(DefaultHeaders)

        dbConnect()

        bind(GuideShopController::class.java).asEagerSingleton()
        bind(GuideShopService::class.java).to(GuideShopServiceImpl::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }
}