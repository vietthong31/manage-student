package application;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SearchDialog extends Application {
	
	private ResourceBundle langSource;
	
	public SearchDialog() {}
	
	public SearchDialog(ResourceBundle source) {
		this.langSource = source;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../controller/Search.fxml"));
		loader.setResources(langSource);
		BorderPane root = loader.load();
		
		Scene scene = new Scene(root);

		primaryStage.setTitle("Tìm kiếm");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../controller/img/student.png")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

}
