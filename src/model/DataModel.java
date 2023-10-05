package model;

import java.sql.SQLException;

import dao.DaoManager;
import dao.JDBCClassDao;
import dao.JDBCFacultyDao;
import dao.JDBCStudentDao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DataModel {

	private static DataModel model = new DataModel();

	private ObservableList<Student> studentList = FXCollections.observableArrayList();
	private ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
	private ObservableList<Class> classList = FXCollections.observableArrayList();

	private FilteredList<Student> filteredStudentList;

	private ObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>();
	private Faculty selectedFaculty;
	private Class selectedClass;

	private DaoManager daoManager = new DaoManager();
	private JDBCClassDao classDao;
	private JDBCStudentDao studentDao;
	private JDBCFacultyDao facultyDao;

	private DataModel() {
		try {
			daoManager.setConnection("jdbc:mysql://localhost:3306/quanlysinhvien", "root", "123456789");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (daoManager.getConnection() != null) {
			classDao = daoManager.createClassDao();
			studentDao = daoManager.createStudentDao();
			facultyDao = daoManager.createFacultyDao();
			studentList = studentDao.getAll();
			facultyList = facultyDao.getAll();
			classList = classDao.getAll();
			filteredStudentList = new FilteredList<>(studentList);
		}
	}

	public static DataModel getInstance() {
		return model;
	}

	public final ObservableList<Student> getStudentList() {
		return studentList;
	}

	public final ObservableList<Faculty> getFacultyList() {
		return facultyList;
	}

	public final FilteredList<Student> getFilteredStudentList() {
		return filteredStudentList;
	}

	public final Faculty getSelectedFaculty() {
		return selectedFaculty;
	}

	public final void setSelectedFaculty(Faculty selectedFaculty) {
		this.selectedFaculty = selectedFaculty;
	}

	public final Class getSelectedClass() {
		return selectedClass;
	}

	public final void setSelectedClass(Class selectedClass) {
		this.selectedClass = selectedClass;
	}

	public final DaoManager getDaoManager() {
		return daoManager;
	}

	public final JDBCClassDao getClassDao() {
		return classDao;
	}

	public final JDBCStudentDao getStudentDao() {
		return studentDao;
	}

	public final JDBCFacultyDao getFacultyDao() {
		return facultyDao;
	}

	public final ObjectProperty<Student> selectedStudentProperty() {
		return this.selectedStudent;
	}

	public final Student getSelectedStudent() {
		return this.selectedStudentProperty().get();
	}

	public final void setSelectedStudent(final Student selectedStudent) {
		this.selectedStudentProperty().set(selectedStudent);
	}

	public final ObservableList<Class> getClassList() {
		return classList;
	}


}
