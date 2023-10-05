package dao;

import java.util.Optional;

import javafx.collections.ObservableList;
import model.Class;

public interface ClassDao {
	Optional<Class> getById(int id);
	ObservableList<Class> getByFacultyId(int id);
	ObservableList<Class> getAll();
}
