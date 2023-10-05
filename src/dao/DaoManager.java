package dao;

import java.sql.*;

public class DaoManager {

	private Connection conn;

	public JDBCFacultyDao createFacultyDao()  {
		JDBCFacultyDao facultyDao = new JDBCFacultyDao(conn);
		return facultyDao;
	}

	public JDBCClassDao createClassDao() {
		JDBCClassDao classDao = new JDBCClassDao(conn);
		return classDao;
	}

	public JDBCStudentDao createStudentDao() {
		JDBCStudentDao studentDao = new JDBCStudentDao(conn);
		return studentDao;
	}

	public void setConnection(String url, String user, String password) throws SQLException {
		conn = DriverManager.getConnection(url, user, password);
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
