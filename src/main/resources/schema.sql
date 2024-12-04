
    CREATE TABLE IF NOT EXISTS authors (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255));

    CREATE TABLE IF NOT EXISTS books (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        author_id BIGINT,
        FOREIGN KEY (author_id) REFERENCES authors(id));
