package ru.astondevs.db.dao

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import ru.astondevs.db.DatabaseFactory.dbQuery
import ru.astondevs.db.mapper.toProjectModel
import ru.astondevs.db.model.ProjectModel
import ru.astondevs.db.table.ProjectTable

class ProjectDao {

    suspend fun getProjects(): List<ProjectModel> {
        return dbQuery {
            ProjectTable.selectAll().map { it.toProjectModel() }
        }
    }

    suspend fun getProjectById(id: Int): ProjectModel? {
        return dbQuery {
            ProjectTable.select { ProjectTable.id eq id }
                .singleOrNull()
                ?.toProjectModel()
        }
    }

    suspend fun createProject(title: String): ProjectModel? {
        return dbQuery {
            ProjectTable.insert {
                it[ProjectTable.title] = title
                it[developersCount] = 0
            }
                .resultedValues
                ?.singleOrNull()
                ?.toProjectModel()
        }
    }

    suspend fun updateProject(projectModel: ProjectModel): Boolean {
        return dbQuery {
            ProjectTable.update({ ProjectTable.id eq projectModel.id }) {
                it[title] = projectModel.title
                it[developersCount] = projectModel.developersCount
            }
        } > 0
    }

    suspend fun deleteProject(id: Int): Boolean {
        return dbQuery {
            ProjectTable.deleteWhere { ProjectTable.id eq id }
        } > 0
    }
}

