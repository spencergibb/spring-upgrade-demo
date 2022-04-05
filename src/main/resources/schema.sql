create table if not exists TODO (
    id INT NOT NULL,
    contents VARCHAR(254) NOT NULL,
    done BOOLEAN NOT NULL
);