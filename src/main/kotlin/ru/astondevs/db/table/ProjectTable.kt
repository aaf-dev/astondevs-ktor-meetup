package ru.astondevs.db.table

import org.jetbrains.exposed.sql.Table

object ProjectTable : Table() {

    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val developersCount = integer("developers_count")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

