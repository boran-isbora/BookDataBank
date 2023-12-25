INSERT INTO publisher_addresses(address, city, country, creation_date) VALUES
('125. sokak Kızılay', 'Ankara', 'Turkey', current_timestamp),
('Alaska sokak Kadıköy', 'İstanbul', 'Turkey', current_timestamp),
('Kordon caddesi Konak', 'İzmir', 'Turkey', current_timestamp),
('65 route de Lyon', 'Paris', 'France', current_timestamp),
('2nd Street Dorm', 'New York', 'USA', current_timestamp);


INSERT INTO publishers(name, publisher_address_id, creation_date) VALUES
('Oxford City Press', 5, current_timestamp),
('Beyaz Kitapevi', 1, current_timestamp),
('Penguin Book Group', 4, current_timestamp),
('Turuncu Basım Yayım', 2, current_timestamp),
('Siyah Yayınevi', 3, current_timestamp);


INSERT INTO authors(name, surname, about, creation_date) VALUES
('Lev Nikolayeviç', 'Tolstoy', 'Ünlü klasik roman yazarı', current_timestamp),
('Hasan', 'Kırmızı', 'İkinci yeni şiirinin öncü şairlerinden biridir.', current_timestamp),
('William', 'Shakespeare', 'İngiliz şair, oyun yazarı ve oyuncu.', current_timestamp),
('Fatma', 'Beyaz', '2010 yılında Nobel Edebiyat Ödülü aldı', current_timestamp),
('Gabriel Garcia', 'Marquez', '1927 Kolombiya doğumlu nobel ödüllü yazar', current_timestamp);


INSERT INTO books(title, publication_date, language, print_length, isbn, cover_image_url, publisher_id, creation_date) VALUES
('Bahçedeki Ördek', '2020-10-25', 'Turkish', 105, '950-876-15-3035-8', 'https://www.example.com/images?id=101', 2, current_timestamp),
('Duvarın Tarihi', '1995-11-30', 'Turkish', 248, '741-564-18-1220-7', 'https://www.example.com/images?id=181', 2, current_timestamp),
('Çam Ağacı Yetiştiriciliği', '2002-09-02', 'Turkish', 721, '154-357-12-4879-1', 'https://www.example.com/images?id=201', 2, current_timestamp),
('Renli Baskı Teknikleri', '2008-06-15', 'Turkish', 50, '963-777-33-3078-3', 'https://www.example.com/images?id=307', 4, current_timestamp),
('Escape From Train', '1996-07-01', 'English', 1587, '749-545-20-5105-6', 'https://www.example.com/images?id=448', 1, current_timestamp),
('Sun of Moon', '1999-08-15', 'English', 125, '888-159-14-5103-5', 'https://www.example.com/images?id=492', 3, current_timestamp),
('Standart Game Rules', '2020-01-01', 'English', 47, '651-161-00-5003-6', 'https://www.example.com/images?id=502', 3, current_timestamp),
('The Age of Innocence', '2005-02-17', 'English', 985, '501-621-25-5088-5', 'https://www.example.com/images?id=600', 3, current_timestamp),
('Walden', '2008-12-21', 'English', 327, '302-261-88-6803-3', 'https://www.example.com/images?id=621', 3, current_timestamp),
('Persuasion', '2012-03-30', 'English', 441, '100-181-77-7503-1', 'https://www.example.com/images?id=650', 3, current_timestamp);

INSERT INTO books_authors(book_id, authors_id, creation_date) VALUES
(1, 2, current_timestamp),
(1, 4, current_timestamp),
(1, 5, current_timestamp),
(2, 2, current_timestamp),
(2, 4, current_timestamp),
(3, 2, current_timestamp),
(4, 4, current_timestamp),
(5, 1, current_timestamp),
(6, 5, current_timestamp);