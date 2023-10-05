package dao;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Class;

import java.sql.*;

public class JDBCClassDao implements ClassDao {
	
	private Connection conn;
	
	public JDBCClassDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Optional<Class> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ObservableList<Class> getByFacultyId(int facultyId) {
		ObservableList<Class> list = FXCollections.observableArrayList();
		String query = "SELECT lop_id, ten_lop FROM lop WHERE khoa_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, facultyId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("lop_id");
				String name = rs.getString("ten_lop");
				list.add(new Class(id, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<Class> getByFacultyName(String facultyName) {
		ObservableList<Class> list = FXCollections.observableArrayList();
		String query = "SELECT lop_id, ten_lop FROM lop WHERE khoa_id = (SELECT khoa_id FROM khoa WHERE ten_khoa = ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, facultyName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("lop_id");
				String name = rs.getString("ten_lop");
				list.add(new Class(id, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ObservableList<Class> getAll() {
		ObservableList<Class> list = FXCollections.observableArrayList();
		String query = "SELECT lop_id, ten_lop FROM lop";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("lop_id");
				String name = rs.getString("ten_lop");
				list.add(new Class(id, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



}
