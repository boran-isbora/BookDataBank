CREATE SEQUENCE publisher_addresses_publisher_address_id_seq start 1 increment 1;

CREATE TABLE publisher_addresses
(
    publisher_address_id bigint NOT NULL DEFAULT nextval('publisher_addresses_publisher_address_id_seq'),
    address varchar(500),
    city varchar(100),
    country varchar(100),
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT pk_publisher_addresses
		PRIMARY KEY (publisher_address_id)
);


CREATE SEQUENCE publishers_publisher_id_seq start 1 increment 1;

CREATE TABLE publishers
(
    publisher_id bigint NOT NULL DEFAULT nextval('publishers_publisher_id_seq'),
    publisher_address_publisher_address_id bigint,
    name varchar(100),
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT pk_publishers
		PRIMARY KEY (publisher_id),
    CONSTRAINT un_publisher_address_publisher_address_id
		UNIQUE (publisher_address_publisher_address_id),
    CONSTRAINT fk_publisher_address_publisher_address_id
		FOREIGN KEY (publisher_address_publisher_address_id)
		REFERENCES publisher_addresses(publisher_address_id)
);


CREATE SEQUENCE authors_author_id_seq start 1 increment 1;

CREATE TABLE authors
(
    author_id bigint NOT NULL DEFAULT nextval('authors_author_id_seq'),
    name varchar(100),
    surname varchar(100),
	about varchar(500),
	creation_date timestamp,
    update_date timestamp,
    CONSTRAINT pk_authors
		PRIMARY KEY (author_id)
);


CREATE SEQUENCE books_book_id_seq start 1 increment 1;

CREATE TABLE books
(
    book_id bigint NOT NULL DEFAULT nextval('books_book_id_seq'),
    publisher_publisher_id bigint,
	title varchar(200),
	publication_date date,
	language varchar(100),
	print_length integer,
	isbn varchar(17),
    cover_image_url varchar(500),
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT pk_books
		PRIMARY KEY (book_id),
    CONSTRAINT fk_publisher_publisher_id
		FOREIGN KEY (publisher_publisher_id)
        REFERENCES publishers (publisher_id)
);


CREATE TABLE books_authors
(
    book_book_id bigint NOT NULL,
    authors_author_id bigint NOT NULL,
    creation_date timestamp,
    update_date timestamp,
	CONSTRAINT fk_book_book_id
		FOREIGN KEY (book_book_id)
        REFERENCES books (book_id),
    CONSTRAINT fk_authors_author_id
		FOREIGN KEY (authors_author_id)
        REFERENCES authors (author_id)
);
