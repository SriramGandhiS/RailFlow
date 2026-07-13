-- Create tables for RailFlow platform

CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role_id BIGINT REFERENCES roles(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL -- RAIL_STATION, METRO_STATION, BUS_STAND
);

CREATE TABLE IF NOT EXISTS routes (
    id BIGSERIAL PRIMARY KEY,
    source_station_id BIGINT REFERENCES stations(id),
    destination_station_id BIGINT REFERENCES stations(id),
    type VARCHAR(50) NOT NULL -- RAIL, METRO, BUS
);

CREATE TABLE IF NOT EXISTS route_stops (
    id BIGSERIAL PRIMARY KEY,
    route_id BIGINT REFERENCES routes(id),
    station_id BIGINT REFERENCES stations(id),
    stop_sequence INT NOT NULL,
    arrival_time TIME,
    departure_time TIME,
    distance_from_source DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicles (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL, -- EXPRESS, SUPERFAST, METRO, GOVT_BUS, PRIVATE_BUS
    operator_name VARCHAR(255) NOT NULL -- Operator e.g., TNSTC, KSRTC, Bangalore Metro, Southern Railways
);

CREATE TABLE IF NOT EXISTS schedules (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT REFERENCES vehicles(id),
    route_id BIGINT REFERENCES routes(id),
    departure_date DATE NOT NULL,
    available_sleeper_seats INT NOT NULL,
    available_ac_seats INT NOT NULL,
    available_general_seats INT NOT NULL,
    base_fare DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    schedule_id BIGINT REFERENCES schedules(id),
    pnr VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(50) NOT NULL, -- CONFIRMED, CANCELLED, WAITING_LISTED
    total_fare DOUBLE PRECISION NOT NULL,
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tickets (
    id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT REFERENCES bookings(id),
    passenger_name VARCHAR(255) NOT NULL,
    passenger_age INT NOT NULL,
    passenger_gender VARCHAR(50) NOT NULL,
    seat_number VARCHAR(50) NOT NULL,
    seat_class VARCHAR(50) NOT NULL, -- SLEEPER, AC, GENERAL
    ticket_status VARCHAR(50) NOT NULL, -- CONFIRMED, CANCELLED
    qr_code_token VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS waiting_list_entries (
    id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT REFERENCES bookings(id),
    schedule_id BIGINT REFERENCES schedules(id),
    seat_class VARCHAR(50) NOT NULL,
    queue_position INT NOT NULL,
    entered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed initial roles
INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');
