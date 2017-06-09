// DeleteCarController.java
package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


public class DeleteCarController implements Initializable {
	
	@FXML private Label messageLabel;
//	@FXML private CheckBox deleteFolderCheckBox;
	
	
	@FXML
	private void handleDeleteButton() {
//		if (deleteFolderCheckBox.isSelected()) {
//			File carFolder = new File("database/Αυτοκίνητα/" + Constants.carToBeDeleted.getLicencePlate());
//			deleteDirectory(carFolder);
//		} // end if
		Car.deleteCar(Constants.carToBeDeleted.getCarID());
		Constants.carDeleted = true;
		Constants.secondaryStage.close();
	} // end of handleDeleteButton method
	
	
	@FXML
	private void handleCancelButton() {
		Constants.secondaryStage.close();
	} // end of handleCancelButton method
	
	
	private boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} // end if
					else {
						files[i].delete();
					} // end else
				} // end for
			} // end if
		} // end if
		return (directory.delete());
	} // end of deleteDirectory method
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		messageLabel.setText("Είστε σίγουροι ότι θέλετε να διαγράψετε το επιλεγμένο συμβόλαιο;");
//		deleteFolderCheckBox.setText("Να γίνει διαγραφή και του φακέλου:\n" 
//		+ "Αυτοκίνητα/" + Constants.carToBeDeleted.getLicencePlate()
//		+ "\nκαθώς και των αρχείων που περιέχει ο φάκελος.");
	} // end of initialize method

} // end of DeleteCarController class
