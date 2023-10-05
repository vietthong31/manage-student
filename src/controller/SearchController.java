package controller;

import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.DataModel;
import model.Student;

public class SearchController {
	private DataModel model = DataModel.getInstance();
	private FilteredList<Student> studentList;
	private StringProperty studentListSize = new SimpleStringProperty();

	@FXML
	private TextField searchField;
	@FXML
	private Button searchBtn;
	@FXML
	private Text resultNumber;

	@FXML
	private TableView<Student> resultTable;
	@FXML
	private TableColumn<Student, String> idColumn;
	@FXML
	private TableColumn<Student, String> lastNameColumn;
	@FXML
	private TableColumn<Student, String> firstNameColumn;

	@FXML
	private Text id;
	@FXML
	private Text lastName;
	@FXML
	private Text firstName;
	@FXML
	private Text dateOfBirth;
	@FXML
	private Text faculty;
	@FXML
	private Text stdClass;
	@FXML
	private Text gender;
	@FXML
	private Text email;

	@FXML
	private void initialize() {
		studentList = new FilteredList<>(model.getStudentList());
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		resultNumber.textProperty().bind(studentListSize);
		studentListSize.set("0");

		resultTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
			if (newValue != null) {
				this.showDetailInfo(newValue);
			}
		});
	}

	@FXML
	private void search() {
		if (searchField.getText().isEmpty()) {
			return;
		}
		studentList.setPredicate(createPredicate(searchField.getText()));
		resultTable.setItems(studentList);

		studentListSize.set(String.valueOf(studentList.size()));

		searchField.requestFocus();
		this.clearText();
		resultTable.refresh();
	}

	@FXML
	private void triggerSearching(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			this.search();
		}
	}

	private void showDetailInfo(Student s) {
		id.setText(s.getId());
		lastName.setText(s.getLastName());
		firstName.setText(s.getFirstName());
		dateOfBirth.setText(s.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		faculty.setText(s.getFacultyName());
		stdClass.setText(s.getClassName());
		gender.setText(s.getGender());
		email.setText(s.getEmail());
	}

	private void clearText() {
		id.setText("");
		lastName.setText("");
		firstName.setText("");
		dateOfBirth.setText("");
		faculty.setText("");
		stdClass.setText("");
		gender.setText("");
		email.setText("");
	}

	private Predicate<Student> createPredicate(String text) {
		return student -> {
			if (student.getId().equals(text) || student.getFirstName().contains(text)
					|| student.getLastName().contains(text)) {
				return true;
			} else {
				return false;
			}
		};
	}
}
