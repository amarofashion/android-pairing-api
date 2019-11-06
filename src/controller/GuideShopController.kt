package com.amaro.controller

import com.amaro.model.GuideShop
import com.amaro.service.GuideShopService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.*
import javax.inject.Inject

class GuideShopController @Inject constructor(application: Application, guideShopService: GuideShopService) {

    init {
        application.routing {
            route("guideShop") {
                get("{id}") {
                    guideShopService.get(call.parameters["id"]?.toInt())
                        ?.run {
                            call.respond(HttpStatusCode.OK, this)
                        } ?: call.respond(HttpStatusCode.BadRequest)
                }

                get("/list") {
                    call.respond(HttpStatusCode.OK, guideShopService.list())
                }

                post {
                    guideShopService.create(call.receiveOrNull<GuideShop>())
                        ?.run {
                            call.respond(HttpStatusCode.Created, this)
                        } ?: call.respond(HttpStatusCode.BadRequest)
                }

                put {
                    guideShopService.update(call.receiveOrNull<GuideShop>())
                        ?.run {
                            call.respond(HttpStatusCode.OK, this)
                        } ?: call.respond(HttpStatusCode.BadRequest)
                }

                delete("{id}") {
                    guideShopService.delete(call.parameters["id"]?.toInt())

                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}