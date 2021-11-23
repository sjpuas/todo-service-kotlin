CREATE TABLE todo(
    id UUID PRIMARY KEY NOT NULL,
    text VARCHAR2(500) NOT NULL,
    status VARCHAR2(6) NOT NULL,
    created_date datetime NOT NULL,
    completed_date datetime NULL
);

