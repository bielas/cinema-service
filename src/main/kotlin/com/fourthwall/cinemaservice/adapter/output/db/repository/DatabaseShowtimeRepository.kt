package com.fourthwall.cinemaservice.adapter.output.db.repository

import com.fourthwall.cinemaservice.adapter.output.db.jpa.ShowtimeJPARepository
import com.fourthwall.cinemaservice.domain.movie.ShowtimeRepository
import org.springframework.stereotype.Repository

@Repository
class DatabaseShowtimeRepository(
    private val showtimeJPARepository: ShowtimeJPARepository
) : ShowtimeRepository {

}
