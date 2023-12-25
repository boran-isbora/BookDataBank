CREATE TABLE publisher_addresses
(
    publisher_address_id bigserial primary key,
    address varchar(500),
    city varchar(100),
    country varchar(100),
    creation_date timestamp,
    update_date timestamp
);

CREATE TABLE publishers
(
    publisher_id bigserial primary key,
    publisher_address_publisher_address_id bigint,
    name varchar(100) not null,
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT un_publisher_address_publisher_address_id
		UNIQUE (publisher_address_publisher_address_id),
    CONSTRAINT fk_publisher_address_publisher_address_id
		FOREIGN KEY (publisher_address_publisher_address_id)
		REFERENCES publisher_addresses(publisher_address_id)
);

CREATE TABLE authors
(
    author_id bigserial primary key,
    name varchar(100) not null,
    surname varchar(100) not null,
	about varchar(500),
	creation_date timestamp,
    update_date timestamp
);


CREATE TABLE books
(
    book_id bigserial primary key,
    publisher_publisher_id bigint,
	title varchar(200) not null,
	publication_date date not null,
	language varchar(100),
	print_length integer,
	isbn varchar(17),
    cover_image_url varchar(500),
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT fk_publisher_publisher_id
		FOREIGN KEY (publisher_publisher_id)
        REFERENCES publishers (publisher_id)
);


CREATE TABLE books_authors
(
    book_book_id bigint not null,
    authors_author_id bigint not null,
    creation_date timestamp,
    update_date timestamp,
	CONSTRAINT fk_book_book_id
		FOREIGN KEY (book_book_id)
        REFERENCES books (book_id),
    CONSTRAINT fk_authors_author_id
		FOREIGN KEY (authors_author_id)
        REFERENCES authors (author_id)
);
