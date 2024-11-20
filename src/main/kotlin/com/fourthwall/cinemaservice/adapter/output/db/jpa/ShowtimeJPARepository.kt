package com.fourthwall.cinemaservice.adapter.output.db.jpa

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.ShowtimeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ShowtimeJPARepository : JpaRepository<ShowtimeEntity, Int>
