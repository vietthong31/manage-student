package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.time.temporal.ChronoUnit;

import dao.JDBCClassDao;
import dao.JDBCStudentDao;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.control.Alert.AlertType;
import model.Class;
import model.DataModel;
import model.Faculty;
import model.Student;

public class FormController {

	private DataModel model;
	private JDBCClassDao classDao;
	private JDBCStudentDao studentDao;
	
	private ChangeListener<Faculty> facultyListener = new FacultyChoiceListener();
	private ChangeListener<Class> classListener = new ClassChoiceListener();
	private StringConverter<LocalDate> dateStringConverter;
	
	private String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
	
	@FXML
	private Text facultyChoicePrompt;
	@FXML
	private Text classChoicePrompt;

	@FXML
	private ToggleGroup genderToggleGroup;
	
	@FXML
	private GridPane formTable;
	@FXML
	protected TextField idField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField firstNameField;
	@FXML
	protected ChoiceBox<Faculty> facultyCb;
	@FXML
	private ChoiceBox<Class> classCb;
	@FXML
	private DatePicker birthDatePicker;
	@FXML
	protected RadioButton male;
	@FXML
	protected RadioButton female;

	@FXML
	protected Button insertBtn;
	@FXML
	protected Button deleteBtn;
	@FXML
	private Button clearBtn;

	public FormController() {
		System.out.println("FormController constructor...");
		genderToggleGroup = new ToggleGroup();
		
		model = DataModel.getInstance();
		classDao = model.getClassDao();
		studentDao = model.getStudentDao();

		dateStringConverter = new StringConverter<LocalDate>() {
			String pattern = "dd-MM-yyyy";
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
			Alert info = MainController.createAlert(AlertType.INFORMATION, "Nhập tuổi lớn hơn 16 và bé hơn 100", "", "Thông báo");
			
			// Xử lý khi chọn ngày
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					long years = ChronoUnit.YEARS.between(date, LocalDate.now());
					if (years >= 16 && years < 100) {
						return dateFormatter.format(date);
					} else {
						info.show();
						return "";
					}
				} else {
					return "";
				}
			}
			
			// Xử lý khi nhập ngày từ bàn phím
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					LocalDate date = LocalDate.parse(string, dateFormatter);
					long years = ChronoUnit.YEARS.between(date, LocalDate.now());
					if (years >= 16 && years < 100) {
						return date;
					} else {
						info.show();
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	@FXML
	public void initialize() {
		System.out.println("FormController init...");

		birthDatePicker.setConverter(dateStringConverter);

		// Faculty ChoiceBox & Class ChoiceBox
		facultyCb.getSelectionModel().selectedItemProperty().addListener(facultyListener);
		classCb.getSelectionModel().selectedItemProperty().addListener(classListener);

		facultyCb.setItems(model.getFacultyList());

		
		UnaryOperator<TextFormatter.Change> nameValidationFormatter = change -> {
			if (change.getControlNewText().isEmpty()) {
				return change;
			}
			if (change.getControlNewText().matches("(([^\\d\\p{Punct}\\s]+)( )?)+")) {
				return change;
			} else {
				System.out.println(change.getText());
				System.out.println(change.getControlNewText());
				MainController.createAlert(AlertType.WARNING, "Chỉ nhập ký tự", "", "Thông báo").show();
				change.setText("");
				return change;
			}
		};

		UnaryOperator<TextFormatter.Change> idValidationFormatter = change -> {
			if (change.getControlNewText().isEmpty()) {
				return change;
			}
			if (change.getControlNewText().matches("[\\w&&[^_]]{1,10}")) {
				return change;
			} else {
				MainController.createAlert(AlertType.WARNING, "Chỉ nhập các kí tự a-z, số 0-9", "", "Thông báo").show();
				change.setText("");
				return change;
			}
		};

		idField.setTextFormatter(new TextFormatter<>(idValidationFormatter));
		firstNameField.setTextFormatter(new TextFormatter<>(nameValidationFormatter));
		lastNameField.setTextFormatter(new TextFormatter<>(nameValidationFormatter));
		
		emailField.textProperty().addListener(e -> {
			boolean active = emailField.getText()!= null && !emailField.getText().isEmpty() && !emailField.getText().matches(emailRegex);
			emailField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), active);
		});
		

		// clear
		clearBtn.setOnAction(event -> {
			model.getFilteredStudentList().setPredicate(createPredicate(null));
			if (model.getSelectedStudent() != null) {
				model.setSelectedStudent(null);
			} else {
				this.resetForm();
			}
			this.clearChoiceBox();
		});

	}
	

	private boolean isAllFilled() {
		for (Node n : formTable.getChildren()) {
			if (n instanceof TextField) {
				if (n.getId().equals("emailField")) {
					continue;
				}
				if (((TextField) n).getText().isEmpty()) {
					n.requestFocus();
					return false;
				}
			} else if (n instanceof DatePicker) {
				if (((DatePicker) n).getValue() == null) {
					n.requestFocus();
					return false;
				}
			}
		}
		if (facultyCb.getSelectionModel().getSelectedItem() == null) {
			facultyCb.requestFocus();
			return false;
		} else if (classCb.getSelectionModel().getSelectedItem() == null) {
			classCb.requestFocus();
			return false;
		}
		if (genderToggleGroup.getSelectedToggle() == null) {
			male.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * Thêm sinh viên mới hoặc cập nhật thông tin sinh viên.
	 */
	@FXML
	private void insertStudent() {
		
		if (!isAllFilled()) {
			MainController.createAlert(AlertType.WARNING, "Chưa điền/ chọn đủ thông tin", "", "Thông báo").show();
			return;
		}

		String email = emailField.getText() == null ? "" : emailField.getText();
		if (!email.isEmpty()) {
			if (!(email.matches(emailRegex))) {
				MainController.createAlert(AlertType.WARNING, "Email không hợp lệ", "", "Thông báo").show();
				emailField.requestFocus();
				return;
			}
		}
		
		String id = this.idField.getText();
		String lastName = this.lastNameField.getText();
		String firstName = this.firstNameField.getText();
		LocalDate birthDate = birthDatePicker.getValue();
		Faculty selectedFaculty = model.getSelectedFaculty();
		Class selectedClass = model.getSelectedClass();
		RadioButton choosedBtn = (RadioButton) genderToggleGroup.getSelectedToggle();
		String gender = choosedBtn.getText().toLowerCase();


		Student newStd = new Student(id, firstName, lastName, birthDate, gender, selectedFaculty.getName(), selectedClass.getName());
		newStd.setEmail(email);

		if (model.getSelectedStudent() == null) {
			boolean inserted;
			try {
				inserted = studentDao.insert(newStd, selectedClass.getId());
				if (inserted) {
					model.getStudentList().add(0, newStd);
					System.out.printf("Đã thêm: %s\n", newStd);
					MainController.createAlert(AlertType.INFORMATION, "Đã thêm " + newStd, "", "Thêm sinh viên").show();
					this.resetForm();
				}
			} catch (SQLException e) {
				MainController.createAlert(AlertType.ERROR, e.getMessage(), "", "Error").show();
			}

		} else {
			boolean updated = studentDao.update(newStd, selectedClass.getId());
			if (updated) {
				int index = model.getStudentList().indexOf(model.getSelectedStudent());
				model.getStudentList().set(index, newStd);
			}
		}
	}

	/**
	 * Xóa sinh viên khỏi TableView
	 */
	@FXML
	private void deleteStudent() {
		Student selected = model.getSelectedStudent();
		boolean isDeleted = studentDao.delete(selected.getId());
		if (isDeleted) {
			model.getStudentList().remove(selected);
		}

	}

	/**
	 * Xóa chữ trong form và bỏ select table (nếu có)
	 */
	protected void resetForm() {
		// fields
		idField.clear();
		emailField.clear();
		lastNameField.clear();
		firstNameField.clear();
		birthDatePicker.setValue(null);
		idField.setDisable(false);
		// radio buttons
		male.setSelected(false);
		female.setSelected(false);
		// choicebox
		facultyCb.setDisable(false);
		classCb.setDisable(false);
		deleteBtn.setDisable(true);
	}

	protected void clearChoiceBox() {
		facultyCb.getSelectionModel().clearSelection();
	}

	@FXML
	private void scale(MouseEvent event) {
		Button sourceBtn = (Button) event.getSource();
		ScaleTransition st = new ScaleTransition(Duration.millis(80), sourceBtn);
		st.setByX(0.1);
		st.setByY(0.1);
		st.setCycleCount(2);
		st.setAutoReverse(true);
		st.play();
	}

	/**
	 * Ràng buộc thuộc tính của sinh viên đang chọn cho field, choicebox.
	 */
	public void showInfo() {
		Student std = model.getSelectedStudent();

		idField.setText(std.getId());
		lastNameField.setText(std.getLastName());
		firstNameField.setText(std.getFirstName());
		birthDatePicker.setValue(std.getDateOfBirth());
		emailField.setText(std.getEmail());

		// Chọn Khoa
		FilteredList<Faculty> oneFaculty = facultyCb.getItems().filtered(faculty -> {
			if (faculty.getName().equals(std.getFacultyName()))
				return true;
			return false;
		});
		Faculty f = oneFaculty.get(0);
		int index = facultyCb.getItems().indexOf(f);
		facultyCb.getSelectionModel().select(index);

		// Chọn Lớp
		FilteredList<Class> oneClass = classCb.getItems().filtered(stdClass -> {
			if (stdClass.getName().equals(std.getClassName()))
				return true;
			return false;
		});
		Class c = oneClass.get(0);
		int index2 = classCb.getItems().indexOf(c);
		classCb.getSelectionModel().select(index2);

		if (std.getGender().equals("male")) {
			male.setSelected(true);
		} else {
			female.setSelected(true);
		}
	}

	class FacultyChoiceListener implements ChangeListener<Faculty> {
		@Override
		public void changed(ObservableValue<? extends Faculty> observable, Faculty oldFaculty, Faculty currentFaculty) {
			if (currentFaculty != null) {
				facultyChoicePrompt.setVisible(false);

				model.setSelectedFaculty(currentFaculty);
				int facultyId = currentFaculty.getId();
				ObservableList<Class> classByFaculty = classDao.getByFacultyId(facultyId);

				// Lọc sinh viên nếu hiện không chọn học sinh trong TableView.
				if (model.getSelectedStudent() == null) {
					model.getFilteredStudentList().setPredicate(createPredicate(currentFaculty.getName()));
					System.out.printf("%s: %d sinh viên\n", currentFaculty.getName(),
							model.getFilteredStudentList().size());
				}

				// Set danh sách Lớp theo Khoa
				classCb.setItems(classByFaculty);
			} else {
				facultyChoicePrompt.setVisible(true);
				classCb.setItems(FXCollections.observableArrayList());
			}
		}
	}

	class ClassChoiceListener implements ChangeListener<Class> {
		@Override
		public void changed(ObservableValue<? extends Class> observable, Class oldValue, Class currentClass) {
			if (currentClass != null) {
				classChoicePrompt.setVisible(false);
				model.setSelectedClass(currentClass);
				if (model.getSelectedStudent() == null) {
					model.getFilteredStudentList().setPredicate(createPredicate(currentClass.getName()));
					System.out.printf("%s: %d sinh viên\n", currentClass.getName(),
							model.getFilteredStudentList().size());
				}
			} else {
				classChoicePrompt.setVisible(true);
			}
		}
	}

	/**
	 * Hàm kiểm tra sinh viên có thuộc 1 khoa/ 1 lớp hay không
	 * 
	 * @param s    Sinh viên
	 * @param text Tên khoa, tên lớp
	 * @return
	 */
	private boolean searchStudent(Student s, String text) {
		if (s.getFacultyName().equals(text) || s.getClassName().equals(text)) {
			return true;
		}
		return false;
	}

	/**
	 * Tạo Predicate
	 * 
	 * @param text Tên khoa, tên lớp
	 * @return
	 */
	private Predicate<Student> createPredicate(String text) {
		return student -> {
			if (text == null || text.isEmpty())
				return true;
			return searchStudent(student, text);
		};
	}


}
