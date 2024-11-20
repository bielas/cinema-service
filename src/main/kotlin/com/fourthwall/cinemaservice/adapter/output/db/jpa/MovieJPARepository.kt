package com.fourthwall.cinemaservice.adapter.output.db.jpa

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MovieJPARepository : JpaRepository<MovieEntity, Int> {
    fun findByBusinessId(businessId: String): MovieEntity?
}
