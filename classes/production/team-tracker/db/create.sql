SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS team (
id int PRIMARY KEY auto_increment,
team VARCHAR,
members VARCHAR,
password VARCHAR,
stateId INT,
);

CREATE TABLE IF NOT EXISTS states (
id int PRIMARY KEY auto_increment,
name VARCHAR
);