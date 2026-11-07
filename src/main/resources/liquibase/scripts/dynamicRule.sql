--liquibase formatted sql

--changeset ds:1
CREATE TABLE dynamic_rule
(
    id             UUID PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    description    TEXT         NOT NULL,
    rule_condition JSONB        NOT NULL
-- JSONB - специальный тип PostgreSQL который позволяет хранить JSON-объекты и делать по ним запросы
);
