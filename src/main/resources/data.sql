CREATE TABLE IF NOT EXISTS person (
    id BIGINT(20) AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL
);
CREATE TABLE IF NOT EXISTS phone (
    id BIGINT(20) AUTO_INCREMENT  PRIMARY KEY,
    person_id BIGINT(20) NOT NULL,
    number VARCHAR(13) NOT NULL,
    type VARCHAR(6) NOT NULL,
    foreign key (person_id) references person(id)
);
