--liquibase formatted sql

--changeset jurgis:001

CREATE TABLE airports (
    id serial PRIMARY KEY,
    country VARCHAR(64),
    city VARCHAR(128),
    airport VARCHAR(8)
);