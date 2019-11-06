package com.amaro.service

import com.amaro.database.GuideShopsTable
import com.amaro.database.insertIntoDatabase
import com.amaro.database.updateIntoDatabase
import com.amaro.model.GuideShop
import com.amaro.model.toGuideShop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class GuideShopServiceImpl : GuideShopService {
    override suspend fun list(): List<GuideShop?> = withContext(Dispatchers.IO) {
        transaction { GuideShopsTable.selectAll().map { it.toGuideShop() } }
    }

    override suspend fun create(newGuideShop: GuideShop?): GuideShop? = withContext(Dispatchers.IO) {
        val id = transaction {
            newGuideShop?.run {
                this.insertIntoDatabase() get GuideShopsTable.id
            }
        }

        return@withContext get(id)
    }

    override suspend fun update(updatedGuideShop: GuideShop?): GuideShop? = withContext(Dispatchers.IO) {
        transaction {
            updatedGuideShop?.run {
                this.updateIntoDatabase()
            }
        }

        return@withContext get(updatedGuideShop?.id)
    }

    override suspend fun get(id: Int?): GuideShop? = withContext(Dispatchers.IO) {
        transaction {
            GuideShopsTable.select { GuideShopsTable.id.eq(id ?: -1) }.firstOrNull()?.toGuideShop()
        }
    }

    override suspend fun delete(id: Int?) = withContext(Dispatchers.IO) {
        transaction {
            GuideShopsTable.deleteWhere {
                GuideShopsTable.id eq (id ?: -1)
            }
        }

        return@withContext
    }
}