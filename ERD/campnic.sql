SHOW tables;

SELECT TABLE_NAME FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'campdb'
;

SELECT * FROM db_user;
SELECT * FROM authority;
SELECT * FROM authreq;
SELECT * FROM Camping;
SELECT * FROM Campsite;
SELECT * FROM Camp_reserve
SELECT * FROM Camp_file;
SELECT * FROM db_city;
SELECT * FROM lender;
SELECT * FROM item;
SELECT * FROM item_file;
SELECT * FROM qna;
SELECT * FROM qna_comment;