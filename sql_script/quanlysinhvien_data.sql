USE quanlysinhvien;

INSERT INTO khoa VALUES
(1, 'Công nghệ thông tin'),
(2, 'Luật'),
(3, 'Kinh tế'),
(4, 'Kỹ thuật cơ - điện tử'),
(5, 'Kiến trúc'),
(6, 'Điện tử viễn thông');

INSERT INTO lop (lop_id, khoa_id, ten_lop) VALUES
(1801, 1, '18CNTT01'),
(1802, 1, '18CNTT02'),
(1803, 1, '18CNTT03'),
(1804, 2, '18LUAT01'),
(1805, 2, '18LUAT02'),
(1806, 2, '18LUAT03'),
(1901, 3, '19QTKD01'),
(1902, 3, '19QTKD02'),
(1903, 3, '19QTKD03'),
(2001, 3, '20QTKD01'),
(1807, 4, '18CĐT01'),
(1808, 4, '18CĐT02'),
(2002, 4, '20CĐT01'),
(2003, 5, '20TKNT01'),
(2004, 5, '20TKNT02'),
(1809, 6, '18ĐTVT01');

INSERT INTO sinhvien VALUES
(1, 1801, 'Huỳnh', 'Mai Hoa', '2000-05-08','female', null),
(2, 1801, 'Phạm', 'Quang Đức', '2000-10-08', 'male', null),
(3, 1801, 'Lê', 'Quốc Bảo', '2000-11-30', 'male', null),
(4, 1801, 'Phan', 'Thành Đạt', '2002-10-03', 'male', null),
(5, 1802, 'Đinh', 'Quốc Hải', '2000-09-11', 'male', null),
(6, 1802, 'Võ', 'Thị Thu Sương', '2000-08-05', 'female', null),
(7, 1802, 'Nguyễn', 'Đức Hưng', '2000-07-01', 'male', null),
(8, 1901, 'Phạm', 'Đình Khánh', '2001-06-29', 'male', null),
(9, 1901, 'Lê', 'Hoàng Long', '2001-05-14', 'male', null),
(10, 1901, 'Đỗ', 'Tấn Đạt', '2001-03-02', 'male', null),
(11, 1902, 'Võ', 'Hoàng Oanh', '2001-09-05', 'female', null),
(12, 1902, 'Phạm', 'Thục Quyên', '2001-07-10', 'female', null),
(13, 1903, 'Trần', 'Nguyên Khang', '2001-10-07', 'male', null),
(14, 2001, 'Nguyễn', 'Thục Hiền', '2002-05-08', 'female', null),
(15, 2001, 'Huỳnh', 'Minh Thư', '2002-06-10', 'female', null),
(16, 2002, 'Lê', 'Anh Phương Thảo', '2002-06-17', 'female', null),
(17, 2002, 'Nguyễn', 'Đình Huy', '2002-03-11', 'male', null),
(18, 2003, 'Võ', 'Kim Anh', '2002-05-18', 'female', 'vtka@test.com'),
(19, 2004, 'Phạm', 'Ngọc Anh', '2002-07-12', 'female', null),
(20, 1803, 'Nguyễn', 'Minh Tú', '2000-07-13', 'male', 'nmt@test.com'),
(21, 1803, 'Nguyễn', 'Minh Tâm', '2000-12-07', 'female', 'nmt@mail.com'),
(22, 1803, 'Huỳnh', 'Hoàng Long', '2000-12-08', 'male', null),
(23, 1804, 'Nguyễn', 'Trâm Anh', '2000-05-09', 'female', null),
(24, 1804, 'Võ', 'Tố Như', '2000-12-12', 'female', null),
(25, 1804, 'Phạm', 'Mỹ Anh', '2000-12-02', 'female', null),
(26, 1805, 'Trần', 'Anh Khoa', '2000-09-02', 'male', null),
(27, 1805, 'Phạm', 'Hoàng Anh', '2000-02-09', 'female', null),
(28, 1805, 'Phạm', 'Huy Hoàng', '2000-12-21', 'male', 'phh@mail.com'),
(29, 1806, 'Hoàng', 'Nam', '2000-12-29', 'male', 'hn@mail.com'),
(30, 1806, 'Hùng', 'Cường', '2000-10-06', 'male', 'hc@mail.com'),
(31, 1806, 'Phạm', 'Thục Quyên', '2000-06-06', 'female', null),
(32, 1807, 'Hoàng', 'Nam', '2000-07-04', 'male', null),
(33, 1807, 'Hùng', 'Cường', '2000-06-10', 'male', null),
(34, 1807, 'Phạm', 'Thục Quyên', '2000-10-08', 'female', null);