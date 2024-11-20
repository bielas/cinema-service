package com.fourthwall.cinemaservice.adapter.output.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "showtimes")
data class ShowtimeEntity(
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalDateTime,

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "last_update_user_email", nullable = false)
    val userEmail: String,
)
