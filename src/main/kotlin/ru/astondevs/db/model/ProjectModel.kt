package ru.astondevs.db.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectModel(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("developersCount")
    val developersCount: Int
)
