--liquibase formatted sql

--changeset jurgis:001

CREATE TABLE flights (
    id serial PRIMARY KEY,
    from_id NUMERIC NOT NULL,
    to_id NUMERIC NOT NULL,
    carrier VARCHAR (255) NOT NULL,
    departure_time VARCHAR(255) NOT NULL,
    arrival_time VARCHAR(255) NOT NULL
);