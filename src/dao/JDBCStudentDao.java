package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;

public class JDBCStudentDao implements StudentDao {

	private Connection conn;

	public JDBCStudentDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Optional<Student> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<Student> getByFacultyId(int facultyId) {
		ObservableList<Student> list = FXCollections.observableArrayList();
		String query = "SELECT * FROM sinhvien_info sv WHERE sv.ten_khoa = (SELECT ten_khoa FROM khoa WHERE khoa_id = ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, facultyId);
			ResultSet rs = stmt.executeQuery();
			list = getResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ObservableList<Student> getByClassId(int classId) {
		ObservableList<Student> list = FXCollections.observableArrayList();
		String query = "SELECT * FROM sinhvien_info sv WHERE sv.ten_lop = (SELECT ten_lop FROM lop WHERE lop_id = ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, classId);
			ResultSet rs = stmt.executeQuery();
			list = getResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ObservableList<Student> getAll() {
		ObservableList<Student> list = FXCollections.observableArrayList();
		String query = "SELECT * FROM sinhvien_info";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			list = getResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean insert(Student s, int classId) throws SQLException {
		String insertStr = "INSERT INTO sinhvien (sinhvien_id, lop_id, ho, ten, ngay_sinh, gioi_tinh, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insertStr);
		stmt.setString(1, s.getId());
		stmt.setInt(2, classId);
		stmt.setString(3, s.getLastName());
		stmt.setString(4, s.getFirstName());
		stmt.setDate(5, Date.valueOf(s.getDateOfBirth()));
		stmt.setString(6, s.getGender());
		if (s.getEmail().isEmpty()) {
			stmt.setNull(7, Types.VARCHAR);
		} else {
			stmt.setString(7, s.getEmail());
		}
		int row = stmt.executeUpdate();
		return row > 0 ? true : false;
	}

	@Override
	public boolean update(Student s, int classId) {
		String updateStr = "UPDATE sinhvien SET lop_id = ?, ho = ?, ten = ?, ngay_sinh = ?, gioi_tinh = ?, email = ? WHERE sinhvien_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(updateStr);
			stmt.setInt(1, classId);
			stmt.setString(2, s.getLastName());
			stmt.setString(3, s.getFirstName());
			stmt.setDate(4, Date.valueOf(s.getDateOfBirth()));
			stmt.setString(5, s.getGender());
			stmt.setString(6, s.getEmail());
			stmt.setString(7, s.getId());
			int row = stmt.executeUpdate();
			return row > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String delete = "DELETE FROM sinhvien WHERE sinhvien_id = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(delete);
			stmt.setString(1, id);
			int row = stmt.executeUpdate();
			return row > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private ObservableList<Student> getResult(ResultSet rs) {
		ObservableList<Student> list = FXCollections.observableArrayList();
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String lastName = rs.getString("ho");
				String firstName = rs.getString("ten");
				LocalDate birthDate = rs.getDate("ngay_sinh").toLocalDate();
				String gender = rs.getString("gioi_tinh");
				String email = rs.getString("email");
				String className = rs.getString("ten_lop");
				String facultyName = rs.getString("ten_khoa");
				Student std = new Student(id, firstName, lastName, birthDate, gender, facultyName, className);
				std.setEmail(email);
				list.add(std);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
