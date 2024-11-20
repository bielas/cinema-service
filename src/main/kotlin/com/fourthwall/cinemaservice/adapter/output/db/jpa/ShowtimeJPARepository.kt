package com.fourthwall.cinemaservice.adapter.output.db.jpa

import com.fourthwall.cinemaservice.adapter.output.db.entity.ShowtimeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ShowtimeJPARepository : JpaRepository<ShowtimeEntity, Int> {
    @Modifying
    @Query("DELETE FROM ShowtimeEntity s WHERE s.movie.id = :movieId")
    fun deleteAllByMovieId(movieId: Int)
}
