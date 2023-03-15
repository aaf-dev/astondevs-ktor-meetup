package ru.astondevs.db.mapper

import org.jetbrains.exposed.sql.ResultRow
import ru.astondevs.db.model.ProjectModel
import ru.astondevs.db.table.ProjectTable

fun ResultRow.toProjectModel(): ProjectModel {
    return ProjectModel(
        id = this[ProjectTable.id],
        title = this[ProjectTable.title],
        developersCount = this[ProjectTable.developersCount]
    )
}

