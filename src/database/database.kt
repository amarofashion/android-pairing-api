package com.amaro.database

import com.amaro.model.GuideShop
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun dbConnect() {
    Database.connect(getConnection())
    transaction { SchemaUtils.create(GuideShopsTable) }

    mutableListOf(
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
    ).forEach { transaction { it.insertIntoDatabase() } }
}

private fun getConnection(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.h2.Driver"
    config.jdbcUrl = "jdbc:h2:mem:test"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()

    return HikariDataSource(config)
}