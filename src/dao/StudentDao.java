package dao;

import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.ObservableList;
import model.Student;

public interface StudentDao {

	Optional<Student> getById(int id);
	ObservableList<Student> getByFacultyId(int id);
	ObservableList<Student> getByClassId(int id);
	ObservableList<Student> getAll();
	boolean insert(Student s, int classId) throws SQLException;
	boolean update(Student s, int classId);
	boolean delete(String id);
	
}
