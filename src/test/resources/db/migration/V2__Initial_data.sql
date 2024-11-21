-- Insert movies
INSERT INTO movies (business_id)
VALUES ('tt0232500'),
       ('tt0322259'),
       ('tt0463985'),
       ('tt1013752'),
       ('tt1596343'),
       ('tt1905041'),
       ('tt2820852'),
       ('tt4630562'),
       ('tt5433138');

-- Insert showtimes
INSERT INTO showtimes (start_time, price, movie_id, last_update_user_email)
VALUES ('2024-11-20 18:00:00', 15.0, 1, 'user1@example.com'),
       ('2024-11-20 21:00:00', 15.0, 1, 'user1@example.com'),
       ('2024-11-20 19:00:00', 12.0, 2, 'user1@example.com'),
       ('2024-11-20 22:00:00', 12.0, 2, 'user1@example.com'),
       ('2024-11-21 17:00:00', 10.0, 3, 'user1@example.com'),
       ('2024-11-21 20:00:00', 10.0, 3, 'user1@example.com'),
       ('2024-11-21 18:30:00', 18.0, 4, 'user1@example.com'),
       ('2024-11-21 21:30:00', 18.0, 4, 'user1@example.com'),
       ('2024-11-22 19:30:00', 20.0, 5, 'user1@example.com'),
       ('2024-11-22 22:30:00', 20.0, 5, 'user1@example.com'),
       ('2024-11-22 16:00:00', 15.0, 6, 'user1@example.com'),
       ('2024-11-22 19:00:00', 15.0, 6, 'user1@example.com'),
       ('2024-11-23 17:30:00', 18.0, 7, 'user1@example.com'),
       ('2024-11-23 20:30:00', 18.0, 7, 'user1@example.com'),
       ('2024-11-23 16:30:00', 12.0, 8, 'user1@example.com'),
       ('2024-11-23 19:30:00', 12.0, 8, 'user1@example.com'),
       ('2024-11-24 18:00:00', 14.0, 9, 'user1@example.com'),
       ('2024-11-24 21:00:00', 14.0, 9, 'user1@example.com');

-- Insert ratings
INSERT INTO movie_ratings (user_email, rating, created_at, movie_id)
VALUES ('user1@example.com', 5, '2024-11-19 10:00:00', 1),
       ('user2@example.com', 4, '2024-11-19 10:30:00', 1),
       ('user3@example.com', 3, '2024-11-19 11:00:00', 2),
       ('user1@example.com', 2, '2024-11-19 11:30:00', 3),
       ('user4@example.com', 4, '2024-11-19 12:00:00', 4),
       ('user5@example.com', 5, '2024-11-19 12:30:00', 5);
