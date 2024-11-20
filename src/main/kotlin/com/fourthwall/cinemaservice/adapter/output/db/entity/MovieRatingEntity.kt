package com.fourthwall.cinemaservice.adapter.output.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(
    name = "movie_ratings", uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_email", "movie_id"])
    ]
)
data class MovieRatingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "user_email", nullable = false)
    val userEmail: String,

    @Column(name = "rating", nullable = false)
    val rating: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
