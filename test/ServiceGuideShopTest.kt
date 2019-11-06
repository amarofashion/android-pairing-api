package com.amaro

import com.amaro.model.GuideShop
import com.amaro.service.GuideShopService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.assertEquals
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

var guideShops = mutableListOf<GuideShop>()

class GuideShopTestService : GuideShopService {
    override suspend fun list(): List<GuideShop?> {
        return guideShops
    }

    override suspend fun create(newGuideShop: GuideShop?): GuideShop? {
        val size = guideShops.size
        val newId = guideShops[size - 1].id + 1

        newGuideShop?.run {
            guideShops.add(GuideShop(newId, name, address, latitude, longitude))
        }

        return get(newId)
    }

    override suspend fun update(updatedGuideShop: GuideShop?): GuideShop? {
        guideShops.replaceAll { if (it.id == updatedGuideShop?.id) updatedGuideShop else it }

        return get(updatedGuideShop?.id)
    }

    override suspend fun get(id: Int?): GuideShop? {
        return guideShops.firstOrNull { it.id == id }
    }

    override suspend fun delete(id: Int?) {
        guideShops.removeIf { it.id == id }
    }
}

class ServiceGuideShopTest {

    private val guideShopService: GuideShopService = GuideShopTestService()

    @Before
    fun before() {

        guideShops = mutableListOf(
            GuideShop(
                1,
                "AMARO Guide Shop Oscar Freire",
                "Rua Oscar Freire, 978 - Jardim Paulista, São Paulo - SP, 01426-000",
                -23.562356,
                -46.6694725
            ),
            GuideShop(
                2,
                "AMARO Guide Shop Pátio Higienópolis",
                "Av. Higienópolis, 618 Shopping Pátio Higienópolis Piso Higienópolis, Loja 3022 - Higienópolis, São Paulo - SP, 01238-000",
                -23.542096677319027,
                -46.658216017167874
            )
        )
    }

    @Test
    fun testGetGuideShops() {
        runBlocking {
            val guideShops: List<GuideShop?> = guideShopService.list()

            assertEquals(guideShops.size, 2)
            assertEquals(guideShops[0]?.id, 1)
            assertEquals(guideShops[0]?.name, "AMARO Guide Shop Oscar Freire")
        }
    }

    @Test
    fun testCreateGuideShop() {
        runBlocking {
            var guideShops: List<GuideShop?> = guideShopService.list()

            assertEquals(guideShops.size, 2)

            val newGuideShop = GuideShop(
                3,
                "AMARO Guide Shop Pátio Paulista",
                "Rua Treze de Maio, 1947 Pátio Paulista Piso Paulista, Loja 3041 - Bela Vista, São Paulo - SP, 01327-001",
                -23.571316,
                -46.6438717
            )
            val result = guideShopService.create(newGuideShop)

            assertNotNull(result)

            guideShops = guideShopService.list()

            assertEquals(guideShops.size, 3)
            assertEquals(guideShops[2]?.name, result.name)
        }
    }

    @Test
    fun testUpdateGuideShop() {
        runBlocking {
            var guideShops: List<GuideShop?> = guideShopService.list()

            assertEquals(guideShops[0]?.name, "AMARO Guide Shop Oscar Freire")

            val result = guideShopService.update(
                GuideShop(
                    1, "AMARO Guide Shop Oscar Freire - Updated",
                    "Rua Oscar Freire, 978 - Jardim Paulista, São Paulo - SP, 01426-000",
                    -23.562356,
                    -46.6694725
                )
            )

            assertNotNull(result)

            guideShops = guideShopService.list()

            assertEquals(guideShops.size, 2)
            assertEquals(guideShops[0]?.name, result.name)
        }
    }

    @Test
    fun testDeleteGuideShop() {
        runBlocking {
            var guideShops: List<GuideShop?> = guideShopService.list()

            assertEquals(guideShops.size, 2)
            assertEquals(guideShopService.get(2)?.name, "AMARO Guide Shop Pátio Higienópolis")

            guideShopService.delete(2)

            guideShops = guideShopService.list()

            assertEquals(guideShops.size, 1)
            assertNull(guideShopService.get(2))
        }
    }
}