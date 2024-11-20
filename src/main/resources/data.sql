-- Insert movies
INSERT INTO movies (id, business_id)
VALUES
    (1, 'tt0232500'),
    (2, 'tt0322259'),
    (3, 'tt0463985'),
    (4, 'tt1013752'),
    (5, 'tt1596343'),
    (6, 'tt1905041'),
    (7, 'tt2820852'),
    (8, 'tt4630562'),
    (9, 'tt5433138');

-- Insert showtimes
INSERT INTO showtimes (id, start_time, price, movie_id, last_update_user_email)
VALUES
    (1, '2024-11-20 18:00:00', 15.0, 1, 'user1@example.com'),
    (2, '2024-11-20 21:00:00', 15.0, 1, 'user1@example.com'),
    (3, '2024-11-20 19:00:00', 12.0, 2, 'user1@example.com'),
    (4, '2024-11-20 22:00:00', 12.0, 2, 'user1@example.com'),
    (5, '2024-11-21 17:00:00', 10.0, 3, 'user1@example.com'),
    (6, '2024-11-21 20:00:00', 10.0, 3, 'user1@example.com'),
    (7, '2024-11-21 18:30:00', 18.0, 4, 'user1@example.com'),
    (8, '2024-11-21 21:30:00', 18.0, 4, 'user1@example.com'),
    (9, '2024-11-22 19:30:00', 20.0, 5, 'user1@example.com'),
    (10, '2024-11-22 22:30:00', 20.0, 5, 'user1@example.com'),
    (11, '2024-11-22 16:00:00', 15.0, 6, 'user1@example.com'),
    (12, '2024-11-22 19:00:00', 15.0, 6, 'user1@example.com'),
    (13, '2024-11-23 17:30:00', 18.0, 7, 'user1@example.com'),
    (14, '2024-11-23 20:30:00', 18.0, 7, 'user1@example.com'),
    (15, '2024-11-23 16:30:00', 12.0, 8, 'user1@example.com'),
    (16, '2024-11-23 19:30:00', 12.0, 8, 'user1@example.com'),
    (17, '2024-11-24 18:00:00', 14.0, 9, 'user1@example.com'),
    (18, '2024-11-24 21:00:00', 14.0, 9, 'user1@example.com');

-- Insert ratings
INSERT INTO movie_ratings (id, user_email, rating, created_at, movie_id)
VALUES
    (1, 'user1@example.com', 5, '2024-11-19 10:00:00', 1),
    (2, 'user2@example.com', 4, '2024-11-19 10:30:00', 1),
    (3, 'user3@example.com', 3, '2024-11-19 11:00:00', 2),
    (4, 'user1@example.com', 2, '2024-11-19 11:30:00', 3),
    (5, 'user4@example.com', 4, '2024-11-19 12:00:00', 4),
    (6, 'user5@example.com', 5, '2024-11-19 12:30:00', 5);
