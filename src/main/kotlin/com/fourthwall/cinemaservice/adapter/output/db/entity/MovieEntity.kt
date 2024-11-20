package com.fourthwall.cinemaservice.adapter.output.db.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "movies")
data class MovieEntity(
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "business_id", nullable = false, unique = true, updatable = false)
    val businessId: String,

    @OneToMany(
        mappedBy = "movie",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val showtimes: List<ShowtimeEntity> = emptyList(),

    @OneToMany(
        mappedBy = "movie",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val ratings: List<MovieRatingEntity> = emptyList()
) {
    fun withUpdatedRatings(newRatings: List<MovieRatingEntity>): MovieEntity {
        return this.copy(ratings = newRatings)
    }
}
