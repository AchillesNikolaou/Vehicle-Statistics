// CarWindowController.java
package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;


public class CarWindowController implements Initializable {

	public static Car carToEdit;

	// fields
	@FXML private TextField licencePlateTextField;
	@FXML private TextField carCompanyTextField;
	@FXML private TextField carModelTextField;
	@FXML private TextField carColorTextField;
	@FXML private TextArea carInfoTextField;
	
	
	
	/** Called when the user clicks register. */
	@FXML
	private void handleRegister() {
		
		if (!Constants.editWasPressed) {
			
			Alert alertWindow;
			
			// check if all fields are empty
			if (licencePlateTextField.getText().trim().isEmpty() &&
					carCompanyTextField.getText().trim().isEmpty() &&
					carModelTextField.getText().trim().isEmpty() &&
					carColorTextField.getText().trim().isEmpty() &&
					carInfoTextField.getText().trim().isEmpty()) {
				
				alertWindow = new Alert(AlertType.ERROR);
				alertWindow.initOwner(Constants.secondaryStage);
				alertWindow.setTitle("Σφάλμα.");
				alertWindow.setHeaderText(null);
				alertWindow.setContentText("Δεν έχετε εισάγει στοιχεία.");
				
				alertWindow.showAndWait();
				
			} // end if
			
			// check if licencePlate is filled
			else if (licencePlateTextField.getText().trim().isEmpty()) {
				alertWindow = new Alert(AlertType.ERROR);
				alertWindow.initOwner(Constants.secondaryStage);
				alertWindow.setTitle("Σφάλμα.");
				alertWindow.setHeaderText(null);
				alertWindow.setContentText("Πρέπει να συμπληρώσετε τo πεδίo Πινακίδα.");
				
				alertWindow.showAndWait();
			} // end else if
			
			// check if record already exists
			else if (Car.entryAlredyExists(0, licencePlateTextField.getText().trim())) {
				alertWindow = new Alert(AlertType.ERROR);
				alertWindow.initOwner(Constants.secondaryStage);
				alertWindow.setTitle("Σφάλμα.");
				alertWindow.setHeaderText(null);
				alertWindow.setContentText("Η \"Πινακίδα\" που εισάγατε υπάρχει ήδη.");
				
				alertWindow.showAndWait();
			} // end else if
			else {
				Car.carData.add(new Car(
						licencePlateTextField.getText().trim(),
						carCompanyTextField.getText().trim(),
						carModelTextField.getText().trim(),
						carColorTextField.getText().trim(),
						carInfoTextField.getText().trim()));
				
//				File customerFolder = new File("database/Αυτοκίνητα/" + licencePlateTextField.getText().trim());
//				customerFolder.mkdir();
			
				Constants.newCarAdded = true;
				Constants.secondaryStage.close();
				
			} // end outer else
		} // end if
		else {
			Alert alertWindow;
			
			// check if all fields are empty
			if (licencePlateTextField.getText().trim().isEmpty() &&
					carCompanyTextField.getText().trim().isEmpty() &&
					carModelTextField.getText().trim().isEmpty() &&
					carColorTextField.getText().trim().isEmpty() &&
					carInfoTextField.getText().trim().isEmpty()) {
				
				alertWindow = new Alert(AlertType.ERROR);
				alertWindow.initOwner(Constants.secondaryStage);
				alertWindow.setTitle("Σφάλμα.");
				alertWindow.setHeaderText(null);
				alertWindow.setContentText("Δεν έχετε εισάγει στοιχεία.");
				
				alertWindow.showAndWait();
			} // end if
			
			// check if the licencePlate is filled
			else if (licencePlateTextField.getText().trim().isEmpty()) {
				alertWindow = new Alert(AlertType.ERROR);
				alertWindow.initOwner(Constants.secondaryStage);
				alertWindow.setTitle("Σφάλμα.");
				alertWindow.setHeaderText("Πρέπει να συμπληρώσετε τo πεδίo Πινακίδα.");
				
				alertWindow.showAndWait();
			} // end if
			
			// check if record already exists
			else if (Car.entryAlredyExists(carToEdit.getCarID(),
					licencePlateTextField.getText().trim())) {
				alertWindow = new Alert(AlertType.ERROR);
				alertWindow.initOwner(Constants.secondaryStage);
				alertWindow.setTitle("Σφάλμα.");
				alertWindow.setHeaderText(null);
				alertWindow.setContentText("Η \"Πινακίδα\" που εισάγατε υπάρχει ήδη.");
				
				alertWindow.showAndWait();
			} // end else if
			else {
//				File oldCarFolder = new File("database/Αυτοκίνητα/" + carToEdit.getLicencePlate());
				
//				File newCarFolder = new File("database/Αυτοκίμητα/" + licencePlateTextField.getText().trim());
				
//				oldCarFolder.renameTo(newCarFolder);
				
				Car.updateCar(carToEdit, new Car(
						carToEdit.getCarID().intValue(),
						licencePlateTextField.getText().trim(),
						carCompanyTextField.getText().trim(),
						carModelTextField.getText().trim(),
						carColorTextField.getText().trim(),
						carInfoTextField.getText().trim()));
				
				Constants.carEdited = true;
				Constants.secondaryStage.close();
			} // end outer else
		} // end else
	} // end of handleRegister method
	
	
	/** Called when the user clicks cancel. */
	@FXML
	private void handleCancel() {
		Constants.secondaryStage.close();
	} // end of method handleCancel
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!Constants.editWasPressed) {
			// set focus to the first field
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					licencePlateTextField.requestFocus();
				} // end run
			}); // end runLater
		} // end if
		else {
			
			// set the field values
			licencePlateTextField.setText(carToEdit.getLicencePlate());
			carCompanyTextField.setText(carToEdit.getCarCompany());
			carModelTextField.setText(carToEdit.getCarModel());
			carColorTextField.setText(carToEdit.getCarColor());
			carInfoTextField.setText(carToEdit.getCarInfo());
			
			// set focus to the first field
			Platform.runLater(new Runnable() {
				@Override
				public void run() {					
					if (Constants.clickedLabel.equals("licencePlateLabel"))
						licencePlateTextField.requestFocus();
					if (Constants.clickedLabel.equals("carCompanyLabel"))
						carCompanyTextField.requestFocus();
					if (Constants.clickedLabel.equals("carModelLabel"))
						carModelTextField.requestFocus();
					if (Constants.clickedLabel.equals("carColorLabel"))
						carColorTextField.requestFocus();
					if (Constants.clickedLabel.equals("carInfoLabel"))
						carInfoTextField.requestFocus();
				}
			}); // end runLater
		} // end else
	} // end of initialize method

	
} // end of CarWindowController class
