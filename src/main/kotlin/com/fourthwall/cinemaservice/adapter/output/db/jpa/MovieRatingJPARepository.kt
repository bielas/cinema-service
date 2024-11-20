package com.fourthwall.cinemaservice.adapter.output.db.jpa

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieRatingEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRatingJPARepository : JpaRepository<MovieRatingEntity, Int>
