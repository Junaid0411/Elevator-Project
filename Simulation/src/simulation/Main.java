package simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage){
		try {
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Configure.fxml"));
			loader.setController(new configureController());
			final Parent root = loader.load();

			final Scene scene = new Scene(root, 280, 450);
			primaryStage.setTitle("Configure Simulation");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		  }
	}

	public static void main(String[] args) {
		launch(args);
	}


}


