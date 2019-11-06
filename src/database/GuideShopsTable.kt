package com.amaro.database

import com.amaro.model.GuideShop
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update

object GuideShopsTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 255)
    val address = varchar("address", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
}

fun GuideShop.insertIntoDatabase() : InsertStatement<Number> {
    return GuideShopsTable.insert {
        with(this@insertIntoDatabase) {
            it[GuideShopsTable.name] = name
            it[GuideShopsTable.address] = address
            it[GuideShopsTable.latitude] = latitude
            it[GuideShopsTable.longitude] = longitude
        }
    }
}

fun GuideShop.updateIntoDatabase() {
    GuideShopsTable.update({ GuideShopsTable.id eq this@updateIntoDatabase.id }) {
        with(this@updateIntoDatabase) {
            it[GuideShopsTable.name] = name
            it[GuideShopsTable.address] = address
            it[GuideShopsTable.latitude] = latitude
            it[GuideShopsTable.longitude] = longitude
        }
    }
}