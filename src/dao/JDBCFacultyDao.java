package dao;

import java.sql.*;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Faculty;

public class JDBCFacultyDao implements FacultyDao {
	
	private Connection conn;
	
	public JDBCFacultyDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Optional<Faculty> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<Faculty> getAll() {
		ObservableList<Faculty> list = FXCollections.observableArrayList();
		String query = "SELECT * FROM khoa";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("khoa_id");
				String name = rs.getString("ten_khoa");
				list.add(new Faculty(id, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean insert(Faculty c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Faculty c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Faculty c) {
		// TODO Auto-generated method stub
		return false;
	}

}
