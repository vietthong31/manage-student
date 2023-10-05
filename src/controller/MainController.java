package controller;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import application.SearchDialog;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Class;

public class MainController {

	private DataModel model;
	
	private Stage mainStage;
	private Stage searchStage;
	
	private RadioMenuItem selectedLanguage;
	
	@FXML
	private BorderPane root;
	@FXML
	private ToggleGroup languageToggleGroup;
	@FXML
	private RadioMenuItem english;
	@FXML
	private RadioMenuItem vietnamese;
	@FXML
	private FormController formController;
	@FXML
	private TableView<Student> studentTable;
	@FXML
	private TableColumn<Student, String> idCol;
	@FXML
	private TableColumn<Student, String> lastNameCol;
	@FXML
	private TableColumn<Student, String> firstNameCol;
	@FXML
	private TableColumn<Student, Faculty> facultyCol;
	@FXML
	private TableColumn<Student, Class> classCol;
	@FXML
	private TableColumn<Student, LocalDate> dateOfBirthCol;
	@FXML
	private TableColumn<Student, String> genderCol;
	@FXML
	private TableColumn<Student, String> emailCol;

	public MainController() {
		System.out.println("MainController constructor...");
	}

	@FXML
	private void initialize() {
		System.out.println("MainController init...");
		
		model = DataModel.getInstance();
		
		selectedLanguage = (RadioMenuItem) languageToggleGroup.getSelectedToggle();
		
		// TableView
		studentTable.setPlaceholder(new Label("Không có sinh viên"));
		studentTable.setItems(model.getFilteredStudentList());

		studentTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
			if (newValue != null) {
				model.setSelectedStudent(newValue);
				formController.idField.setDisable(true);
				formController.deleteBtn.setDisable(false);
				formController.facultyCb.setDisable(true);
				formController.showInfo();
			} else {
				formController.resetForm();
			}
		});
		
		model.selectedStudentProperty().addListener((obs, old, newValue) -> {
			if (newValue == null) {
				studentTable.getSelectionModel().clearSelection();
			}
		});

		// 8 TableColumn
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		facultyCol.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
		classCol.setCellValueFactory(new PropertyValueFactory<>("className"));
		dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

	}

	/**
	 * Hiện cửa sổ thông tin chương trình
	 */
	@FXML
	private void showAboutUs() {
		Alert about = createAlert(AlertType.INFORMATION, "", "", "Giới thiệu");
		about.setHeaderText("An application for managing student information");
		about.setContentText("All icons are designed by Freepik.");
		Image icon = new Image(getClass().getResourceAsStream("img/student64.png"));
		about.setGraphic(new ImageView(icon));
		about.show();
	}

	/**
	 * Hiện cửa sổ hỏi xác nhận thoát chương trình
	 */
	@FXML
	private void askConfirm() {
		Alert confirm = createAlert(AlertType.CONFIRMATION, "Thoát chương trình?", "", "Thoát");
		confirm.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				model.getDaoManager().close();
				Platform.exit();
			}
		});
	}
	
	/**
	 * Chạy cửa sổ tìm kiếm theo ngôn ngữ hiện tại
	 */
	@FXML
	private void launchSearchDialog() {
		ResourceBundle langSource = null;
		switch (selectedLanguage.getId()) {
		case "english":
			langSource = ResourceBundle.getBundle("bundles.lang", Locale.ENGLISH);
			break;
		case "vietnamese":
			langSource = ResourceBundle.getBundle("bundles.lang", new Locale("vi", "VN"));
			break;
		default:
			break;
		}
		
		SearchDialog sd = new SearchDialog(langSource);
		searchStage = new Stage();
		try {
			sd.start(searchStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void changeLanguage(ActionEvent e) {
		RadioMenuItem item = (RadioMenuItem) e.getSource();
		if (item == selectedLanguage) {
			return;
		} else {
			selectedLanguage = item;
		}
		
		Locale locale = null;
		if (item.getText().equals("English")) {
			locale = Locale.ENGLISH;
		} else if (item.getText().equals("Tiếng Việt")) {
			locale = new Locale("vi", "VN");
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		mainStage.close();
		if (searchStage != null) {
			searchStage.close();
		}
		// Tải lại fxml mới, dùng ngôn ngữ được chọn
		try {
			loader.setResources(ResourceBundle.getBundle("bundles.lang", locale));
			BorderPane root = loader.load();
			MainController controller = loader.getController();
			controller.setMainStage(mainStage);
			switch (locale.getLanguage()) {
			case "en":
				controller.languageToggleGroup.selectToggle(controller.english);
				break;
			case "vi":
				controller.languageToggleGroup.selectToggle(controller.vietnamese);
				break;
			default:
				break;
			}
			controller.selectedLanguage = (RadioMenuItem) controller.languageToggleGroup.getSelectedToggle();
			mainStage.setScene(new Scene(root));
			mainStage.show();
		} catch (Exception ex) {
			MainController.createAlert(AlertType.ERROR, ex.getMessage(), "", "Error").showAndWait();
		}
	}
	
	public static Alert createAlert(AlertType type, String content, String header, String title) {
		Alert alert = new Alert(type, content);
		alert.setHeaderText(header);
		alert.setTitle(title);
		return alert;
	}

	public final Stage getSearchStage() {
		return searchStage;
	}
	
	public final void setMainStage(Stage s) {
		this.mainStage = s;
	}
	

}
