CREATE TABLE publisher_addresses
(
    id bigserial primary key,
    address varchar(500),
    city varchar(100),
    country varchar(100),
    creation_date timestamp,
    update_date timestamp
);

CREATE TABLE publishers
(
    id bigserial primary key,
    publisher_address_id bigint,
    name varchar(100) not null,
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT un_publisher_address_id
		UNIQUE (publisher_address_id),
    CONSTRAINT fk_publisher_address_id
		FOREIGN KEY (publisher_address_id)
		REFERENCES publisher_addresses(id)
);

CREATE TABLE authors
(
    id bigserial primary key,
    name varchar(100) not null,
    surname varchar(100) not null,
	about varchar(500),
	creation_date timestamp,
    update_date timestamp
);


CREATE TABLE books
(
    id bigserial primary key,
    publisher_id bigint,
	title varchar(200) not null,
	publication_date date not null,
	language varchar(100),
	print_length integer,
	isbn varchar(17),
    cover_image_url varchar(500),
    creation_date timestamp,
    update_date timestamp,
    CONSTRAINT fk_publisher_id
		FOREIGN KEY (publisher_id)
        REFERENCES publishers (id)
);


CREATE TABLE books_authors
(
    book_id bigint not null,
    authors_id bigint not null,
    creation_date timestamp,
    update_date timestamp,
	CONSTRAINT fk_book_id
		FOREIGN KEY (book_id)
        REFERENCES books (id),
    CONSTRAINT fk_authors_id
		FOREIGN KEY (authors_id)
        REFERENCES authors (id)
);
