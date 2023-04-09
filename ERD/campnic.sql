SHOW tables;

SELECT TABLE_NAME FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'campdb'
;

SELECT * FROM db_user;
SELECT * FROM authority;
SELECT * FROM db_user_authorities;
SELECT * FROM authreq;
SELECT * FROM camping;
SELECT * FROM campsite;
SELECT * FROM camp_reserve;
SELECT * FROM camp_file;
SELECT * FROM coupon;
SELECT * FROM db_city;
SELECT * FROM lender;
SELECT * FROM item;
SELECT * FROM item_file;
SELECT * FROM rental_recipt;
SELECT * FROM qna;
SELECT * FROM qna_comment;

DROP TABLE IF EXISTS rental_recipt;
DROP TABLE IF EXISTS item_file;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS lender;
DROP TABLE IF EXISTS camp_reserve;
DROP TABLE IF EXISTS campsite;
DROP TABLE IF EXISTS camping;
DROP TABLE IF EXISTS db_city;

# 페이징 테스트용 다량의 데이터
INSERT INTO qna(user_id, subject, content, reg_date)
SELECT user_id, subject, content, now(6) FROM qna;
