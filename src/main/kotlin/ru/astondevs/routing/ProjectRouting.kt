package ru.astondevs.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import ru.astondevs.db.dao.ProjectDao
import ru.astondevs.db.model.ProjectModel

fun Route.projectRouting() {
    val dao = ProjectDao()

    route("/projects") {

        get("/all") {
            val projects = dao.getProjects()

            return@get if (projects.isNotEmpty()) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = projects
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "Projects not found"
                )
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Missed \"id\" parameter"
            )

            val project = dao.getProjectById(id)

            return@get if (project != null) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = project
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "Project with id=$id not found"
                )
            }
        }

        post("/add") {
            val projectTitle = call.receiveText()

            return@post if (projectTitle.isEmpty()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "\"title\" can't be empty"
                )
            } else {
                val createdProject = dao.createProject(projectTitle)

                if (createdProject != null) {
                    call.respond(
                        status = HttpStatusCode.Created,
                        message = createdProject
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = "Can't create project"
                    )
                }
            }
        }

        put("/update") {

            val project = call.receive<ProjectModel>()
            val isUpdated = dao.updateProject(project)

            return@put if (isUpdated) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "Project updated"
                )
            } else {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = "Can't update project"
                )
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Missed \"id\" parameter"
            )

            val isDeleted = dao.deleteProject(id)
            if (isDeleted) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "Project with id=$id deleted"
                )
            } else {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = "Can't delete project"
                )
            }
        }
    }
}

