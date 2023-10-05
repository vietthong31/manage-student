DROP SCHEMA IF EXISTS quanlysinhvien;
CREATE SCHEMA quanlysinhvien CHARACTER SET 'utf8mb4';
USE quanlysinhvien;


-- Tạo 3 bảng: khoa, lop, sinhvien

CREATE TABLE khoa (
  khoa_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  ten_khoa VARCHAR(100) NOT NULL,
  PRIMARY KEY (khoa_id)
);

CREATE TABLE lop (
  lop_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  khoa_id SMALLINT UNSIGNED NOT NULL,
  ten_lop VARCHAR(50) NOT NULL,
  PRIMARY KEY (lop_id),
  CONSTRAINT `fk_lop_khoa` FOREIGN KEY (khoa_id) REFERENCES khoa (khoa_id)
);

CREATE TABLE sinhvien (
  sinhvien_id VARCHAR(10) NOT NULL,
  lop_id SMALLINT UNSIGNED NOT NULL,
  ho VARCHAR(20) NOT NULL,
  ten VARCHAR(50) NOT NULL,
  ngay_sinh DATE NOT NULL,
  gioi_tinh ENUM("male", "female") NOT NULL,
  email VARCHAR(320),
  PRIMARY KEY (sinhvien_id),
  CONSTRAINT `fk_sinhvien_lop` FOREIGN KEY (lop_id) REFERENCES lop (lop_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Tạo view: sinhvien_info

CREATE VIEW sinhvien_info AS
SELECT sinhvien_id id, ho, ten, ngay_sinh, gioi_tinh, email, lop.ten_lop, khoa.ten_khoa FROM sinhvien sv
INNER JOIN lop ON sv.lop_id = lop.lop_id
INNER JOIN khoa ON lop.khoa_id = khoa.khoa_id;

-- Trigger

DELIMITER //
CREATE TRIGGER upd_sinhvien BEFORE UPDATE ON sinhvien FOR EACH ROW
BEGIN
    IF (SELECT khoa_id FROM lop WHERE lop_id = NEW.lop_id) <> (SELECT khoa_id FROM lop WHERE lop_id = OLD.lop_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Không chuyển lớp ở khác khoa";
    END IF;
END //
DELIMITER ;



