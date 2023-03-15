package ru.astondevs.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import ru.astondevs.routing.projectRouting

fun Application.configureRouting() {
    routing {
        projectRouting()
    }
}

