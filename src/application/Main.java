package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../controller/Main.fxml"));
		loader.setResources(ResourceBundle.getBundle("bundles.lang", Locale.ENGLISH));
		BorderPane root = loader.load();
		MainController mainController = loader.getController();
		mainController.setMainStage(primaryStage);
		
		Scene scene = new Scene(root);

		primaryStage.setTitle("Quản lý sinh viên");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../controller/img/student.png")));
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			if (mainController.getSearchStage() != null) {
				mainController.getSearchStage().close();
			}
		});
	}
	
	@Override
	public void stop() throws Exception {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
