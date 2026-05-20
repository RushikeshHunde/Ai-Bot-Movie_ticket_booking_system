
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE shows;
TRUNCATE TABLE movie;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Movies for Nanded
INSERT INTO movie (id, title, genre, duration_minutes, language, rating, image_url, city, theatre_name, theatre_address, cinema_type) VALUES 
(1, 'Pushpa 2: The Rule', 'Action', 180, 'Telugu', 9.2, 'https://image.tmdb.org/t/p/original/m9mU809S0pB3fP0k6qN1XfG8.jpg', 'Nanded', 'PVR Nanded', 'Vazirabad, Nanded', 'IMAX'),
(2, 'Sky Force', 'Thriller', 145, 'Hindi', 8.5, 'https://image.tmdb.org/t/p/original/6v8yX7H8kR2G0R7jFp2J2X8.jpg', 'Nanded', 'Cinepolis', 'Shyam Nagar, Nanded', 'PVR');

-- Insert Movies for Mumbai (Upcoming)
INSERT INTO movie (id, title, genre, duration_minutes, language, rating, image_url, city, theatre_name, theatre_address, cinema_type) VALUES 
(3, 'Emergency', 'Drama', 130, 'Hindi', 7.8, 'https://image.tmdb.org/t/p/original/8G3tP5J6uQ9rF1V2z6w9x3.jpg', 'Mumbai', 'Upcoming', 'TBD', 'Standard');

-- Insert Shows for the movies
INSERT INTO shows (id, movie_id, hall_number, total_seats, available_seats, start_time, status) VALUES 
(1, 1, 'Screen 1', 100, 100, '2026-02-25 18:00:00', 'ACTIVE'),
(2, 2, 'Screen 2', 80, 80, '2026-02-25 21:00:00', 'ACTIVE');