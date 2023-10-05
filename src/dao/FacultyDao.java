package dao;

import java.util.Optional;

import javafx.collections.ObservableList;
import model.Faculty;

public interface FacultyDao {
	Optional<Faculty> getById(int id);
	ObservableList<Faculty> getAll();
	boolean insert(Faculty c);
	boolean update(Faculty c);
	boolean delete(Faculty c);
}
