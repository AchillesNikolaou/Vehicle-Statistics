// Constants.java
package application;

import java.io.IOException;

import javax.print.PrintService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Constants {
	
	// stage dimensions
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	// main stage
	public static Stage primaryStage;
	
	// secondary stage
	public static Stage secondaryStage;
	
	// paths of FXML files
	public static final String MAIN_WINDOW_FXML = "MainWindow.fxml";
	public static final String CAR_WINDOW_FXML = "CarWindow.fxml";
	public static final String DELETE_CAR_WINDOW_FXML = "DeleteCarWindow.fxml";
	public static final String ABOUT_DIALOG_FXML = "AboutDiagol.fxml";
	
	public static boolean newCarAdded;
	public static boolean carEdited;
	public static boolean carDeleted;
	public static Car carToBeDeleted;
	
	public static boolean editWasPressed;
	
	// printing
	public static PrintService printers[];
	public static PrintService chosenPrinter;
	public static String imageToPrintPath;
	public static boolean print;
	
	// data has been changed
	public static boolean changesHaveBeenMade = false;
	
	// double-clicked field
	public static String clickedLabel = "";
	
	
	/** Changes the current scene of the screen. */
	public static void changeScreen(String filePath) {
		try {
			// load up another FXML document
			Parent root = FXMLLoader.load(Constants.class.getResource(filePath));
			// create a new scene with root and the stage
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			primaryStage.setScene(scene);
			
		} // end try
		catch (IOException e) {
			System.out.println(e.getMessage() + "\n\n"); // print the exception message
			e.printStackTrace();
		} // end catch
	} // end of changeScreen method
	
	
	public static Image getApplicationIcon() {
		return new Image(Constants.class.getResourceAsStream("car_icon.png"));
	} // end of getApplicationIcon method

} // end of Constants class
