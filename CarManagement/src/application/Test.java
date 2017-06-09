// Test.java
package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Test extends Application {
	
	
	// main screen
	private Parent parent;
	private Scene introScreenScene;
	public static boolean alreadyRunning; 
	
	
	@Override
	public void start(Stage primaryStage) {
		
		// get stage reference
		Constants.primaryStage = primaryStage;
		
		
		// create first scene
		createIntroScrene();
		
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(612);
		primaryStage.setScene(introScreenScene);
		primaryStage.getIcons().add(Constants.getApplicationIcon()); // change icon
		primaryStage.setTitle("Στατιστική Οχημάτων");
		primaryStage.setResizable(true);
		primaryStage.show();
	
	} // end of start method
	
	
	/** Creates the first scene of the application. */
	private void createIntroScrene() {
		try {
			parent = FXMLLoader.load(getClass().getResource(Constants.MAIN_WINDOW_FXML));
			introScreenScene = new Scene(parent, Constants.WIDTH, Constants.HEIGHT);
		} // end of try
		catch (IOException e) {
			e.printStackTrace();
		} // end of catch
	} // end of createIntroSceme method
	
	
	/** The main method starts the program. */
	public static void main(String[] args) {
		launch(args);
	} // end of main method

} // end of Test class
