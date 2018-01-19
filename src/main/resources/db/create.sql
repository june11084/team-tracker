SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS teams (
id int PRIMARY KEY auto_increment,
team VARCHAR,
members ARRAY,
password VARCHAR,
statId INT,
);

CREATE TABLE IF NOT EXISTS states (
id int PRIMARY KEY auto_increment,
name VARCHAR
);
