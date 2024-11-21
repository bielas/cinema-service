-- Create Movies Table
CREATE TABLE movies
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    business_id VARCHAR(255) NOT NULL UNIQUE
);

-- Create Showtimes Table
CREATE TABLE showtimes
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    start_time             TIMESTAMP    NOT NULL,
    price DOUBLE NOT NULL,
    last_update_user_email VARCHAR(255) NOT NULL,
    movie_id               INT          NOT NULL,
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movies (id) ON DELETE CASCADE
);

-- Create Movie Ratings Table
CREATE TABLE movie_ratings
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    rating DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    movie_id   INT          NOT NULL,
    CONSTRAINT fk_movie_rating FOREIGN KEY (movie_id) REFERENCES movies (id) ON DELETE CASCADE
);
