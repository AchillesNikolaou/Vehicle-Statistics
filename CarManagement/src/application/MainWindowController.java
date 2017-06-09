// MainWindowController.java
package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainWindowController implements Initializable {
	
	
	// customer search field
	@FXML private TextField carSearchTextField;
	
	// car table
	@FXML private TableView<Car> carTable;
	@FXML private TableColumn<Car, String> carLicencePlateColumn;
	@FXML private TableColumn<Car, String> carCompanyColumn;
	@FXML private TableColumn<Car, String> carModelColumn;
	@FXML private TableColumn<Car, String> carColorColumn;
	
	// car labels
	@FXML private Label carLicencePlateLabel;
	@FXML private Label carCompanyLabel;
	@FXML private Label carModelLabel;
	@FXML private Label carColorLabel;
	@FXML private Label carInfoLabel;
	
	@FXML private Label messageLabel;
	
	// transitions for animating the message
	private TranslateTransition transitionOut;
	private TranslateTransition transitionIn;
	private TranslateTransition transitionOut2;
	
	
	@FXML
	private void handleNewCar() {
		
		Constants.editWasPressed = false;
		
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainWindowController.class.getResource(Constants.CAR_WINDOW_FXML));
			Parent root = loader.load();
			
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			Constants.secondaryStage = dialogStage;
			Constants.secondaryStage.setTitle("Εισαγωγή νέου Οχήματος");
			Constants.secondaryStage.initModality(Modality.WINDOW_MODAL);
			Constants.secondaryStage.initOwner(Constants.primaryStage);
			Constants.secondaryStage.setResizable(false);
			Constants.secondaryStage.getIcons().add(Constants.getApplicationIcon()); // change icon
			Scene scene = new Scene(root);
			Constants.secondaryStage.setScene(scene);
		
			// Show the dialog and wait until the user closes it
			Constants.secondaryStage.showAndWait();
			
			// notify user if a new customer is added
			if (Constants.newCarAdded) {
//				if (autoSaveCheck.isSelected()) {
					saveCar();
					changeMessageLabelText("Το νέο όχημα προστέθηκε με επιτυχία "
							+ "και αποθηκεύτηκε.");
//				} // end inner if
//				else {
//					changeMessageLabelText("Το νέο συμβόλαιο προστέθηκε με επιτυχία.");
//				} // end else
				Constants.newCarAdded = false;
			} // end outer if
			
		} // end try
		catch (IOException e) {
			e.printStackTrace();
		} // end catch
	} // end of handleNewCar method
	
	
	/** Called when the user clicks the edit car button.
	 *  Opens a dialog to edit details for the chosen car. */
	@FXML
	private void handleEditCar() {
		
		Constants.editWasPressed = true;
		
		Car selectedCar = carTable.getSelectionModel().getSelectedItem();
		int row = carTable.getSelectionModel().getSelectedIndex();
		
		// check if any car is selected
		if (selectedCar != null) {
			try {
				
				CarWindowController.carToEdit = selectedCar;
				
				// Load the fxml file and create a new stage for the pop-up dialog.
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource(Constants.CAR_WINDOW_FXML));
				Parent root = loader.load();
				
				// Create the dialog Stage.
				Stage dialogStage = new Stage();
				Constants.secondaryStage = dialogStage;
				Constants.secondaryStage.setTitle("Επεξεργασία Οχήματος");
				Constants.secondaryStage.initModality(Modality.WINDOW_MODAL);
				Constants.secondaryStage.initOwner(Constants.primaryStage);
				Constants.secondaryStage.setMinWidth(400);
				Constants.secondaryStage.setMinHeight(400);
				Constants.secondaryStage.setResizable(false);
				Constants.secondaryStage.getIcons().add(Constants.getApplicationIcon()); // change icon
				Scene scene = new Scene(root);
				Constants.secondaryStage.setScene(scene);
			
				// Show the dialog and wait until the user closes it
				Constants.secondaryStage.showAndWait();
				
				
				carTable.getSelectionModel().select(row);
				
				// notify user if the car is edited
				if (Constants.carEdited) {
//					if (autoSaveCheck.isSelected()) {
						saveCar();
						changeMessageLabelText("Η επεξεργασία ολοκληρώθηκε με επιτυχία "
								+ "και αποθηκεύτηκε.");
//					} // end inner if
//					else {
//						changeMessageLabelText("Η επεξεργασία ολοκληρώθηκε με επιτυχία.");
//					}
					Constants.carEdited = false;
				} // end outer if
				
			} // end try
			catch (IOException e) {
				e.printStackTrace();
			} // end catch
		} // end if
		else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Constants.primaryStage);
			alert.initModality(Modality.WINDOW_MODAL);
	        
			alert.setTitle("Προσοχή!");
			alert.setHeaderText(null);
			alert.setContentText("Επιλέξτε μια εγγραφή από τον πίνακα οχημάτων.");
			
			alert.showAndWait();
        } // end else
		
	} // end of handleEditCar method
	
	
	@FXML
	private void handleDeleteCar() {
		int selectedIndex = carTable.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex >= 0) {
			// notify the user
			
			// get the ID of the selected car
			int carID =  carTable.getSelectionModel().getSelectedItem().getCarID();
			
			// find which that car is
			Constants.carToBeDeleted = Car.getCarByID(carID);
			
			try {
				// Load the fxml file and create a new stage for the pop-up dialog.
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource(
						Constants.DELETE_CAR_WINDOW_FXML));
				Parent root;
				root = loader.load();
				
				// Create the dialog Stage.
				Stage dialogStage = new Stage();
				Constants.secondaryStage = dialogStage;
				Constants.secondaryStage.setTitle("Διαγραφή Οχήματος");
				Constants.secondaryStage.initModality(Modality.WINDOW_MODAL);
				Constants.secondaryStage.initOwner(Constants.primaryStage);
				Constants.secondaryStage.setResizable(false);
				Constants.secondaryStage.getIcons().add(Constants.getApplicationIcon()); // change icon
				Scene scene = new Scene(root);
				Constants.secondaryStage.setScene(scene);
				
				// Show the dialog and wait until the user closes it
				Constants.secondaryStage.showAndWait();
			} // end try
			catch (IOException e) {
				e.printStackTrace();
			} // end catch
			
			if (Constants.carDeleted) {
				changeMessageLabelText("Το όχημα διεγράφει.");
				Constants.carDeleted = false;
			} // end if
//			if (autoSaveCheck.isSelected()) {
				saveCar();
//			} // end inner if
		} // end if
			
		else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Constants.primaryStage);
			alert.setTitle("Προειδοποίηση!");
			alert.setHeaderText(null);
			alert.setContentText("Δεν έχετε επιλέξει κανένα όχημα.");
			
			alert.showAndWait();
		} // end else
	} // end of handleDeleteCustomer method
	
	
	public void changeMessageLabelText(String message) {
		
		// check if there is already a transition
		if (transitionOut.getStatus() == Status.RUNNING)
			transitionOut.stop();
		
		if (transitionIn.getStatus() == Status.RUNNING)
			transitionIn.stop();
		
		if (transitionOut2.getStatus() == Status.STOPPED)
			messageLabel.setText("");
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				messageLabel.setText(message);
			} // end of handle method
		}) , new KeyFrame(Duration.seconds(1)));
		
		transitionOut.playFromStart();
		timeline.playFromStart();
		transitionIn.playFromStart();
		
		transitionOut2.playFromStart();
		
	} // end of changeMessageLabelText method
	
	
	/** Fills all text fields to show details about the car. If the specified
	 *  car is null, all text fields are cleared. */
	private void showCarDetails(Car car) {
		
		if (car != null) {
			// Fill the labels with info from the car object.
			
			// fill the licencePlateLabel
			if (car.getLicencePlate().equals("")) { carLicencePlateLabel.setText("-"); }
			else { carLicencePlateLabel.setText(car.getLicencePlate()); }
			carLicencePlateLabel.setCursor(Cursor.HAND); // set the cursor to hand
			
			// fill the carCompanyLabel
			if (car.getCarCompany().equals("")) { carCompanyLabel.setText("-"); }
			else { carCompanyLabel.setText(car.getCarCompany()); }
			carCompanyLabel.setCursor(Cursor.HAND); // set the cursor to hand
			
			// fill the carModelLabel
			if (car.getCarModel().equals("")) { carModelLabel.setText("-"); }
			else { carModelLabel.setText(car.getCarModel()); }
			carModelLabel.setCursor(Cursor.HAND); // set the cursor to hand
			
			// fill the carColorLabel
			if (car.getCarColor().equals("")) { carColorLabel.setText("-"); }
			else { carColorLabel.setText(car.getCarColor()); }
			carColorLabel.setCursor(Cursor.HAND); // set the cursor to hand
			
			// fill the carInfoLabel
			if (car.getCarInfo().equals("")) { carInfoLabel.setText("-"); }
			else { carInfoLabel.setText(car.getCarInfo()); }
			carInfoLabel.setCursor(Cursor.HAND); // set the cursor to hand
			
			
		}
		else {
			// Car is null, remove all the text.
			carLicencePlateLabel.setText("");
			carCompanyLabel.setText("");
			carModelLabel.setText("");
			carColorLabel.setText("");
			carInfoLabel.setText("");
			
			carLicencePlateLabel.setCursor(Cursor.HAND); // set the cursor to default
			carCompanyLabel.setCursor(Cursor.HAND); // set the cursor to default
			carModelLabel.setCursor(Cursor.HAND); // set the cursor to default
			carColorLabel.setCursor(Cursor.HAND); // set the cursor to default
			carInfoLabel.setCursor(Cursor.HAND); // set the cursor to default
		} // end else
	} // end of showCarDetails method
	
	
	@FXML
	private void saveCar() {
		File carFile = new File("database/car_data.xml");
		
		try {
			JAXBContext context = JAXBContext.newInstance(CarListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// Wrapping car data.
			CarListWrapper wrapper = new CarListWrapper();
			wrapper.setCars(Car.carData);
			
			// Marshalling and saving XML to the file.
			m.marshal(wrapper, carFile);
			
			changeMessageLabelText("Η αποθήκευση ολοκληρώθηκε.");
		} // end try
		catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Σφάλμα.");
			alert.setHeaderText("Δεν είναι δυνατή η αποθήκευση δεδομένων.");
			
			alert.showAndWait();
		} // end catch
	} // end of saveCar method
	
	
	private void loadCarRecords() {
		File carFile = new File("database/car_data.xml");
		
		if (!carFile.exists())
			return;
		
		try {
			JAXBContext context = JAXBContext.newInstance(CarListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			// Reading XML from the file and unmarshalling.
			CarListWrapper wrapper = (CarListWrapper) um.unmarshal(carFile);

			if (wrapper.getCars() != null) {
				Car.carData.clear();
				Car.carData.addAll(wrapper.getCars());
			}
		
		} // end try
		catch (Exception e) { // catches ANY exception
			e.printStackTrace();
        } // end catch
		
	} // end of loadCarRecords method
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// load data from *.xml files
		loadCarRecords();
		
		// Initialize the car table with the columns.
		carLicencePlateColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("licencePlate"));
		carCompanyColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carCompany"));
		carModelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carModel"));
		carColorColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carColor"));
		
		// Add the data to the table.
		carTable.setItems(Car.carData);
		
		showCarDetails(null); // Clear car details.
		
		// Listen for selection changes and show the car details when changed.
		carTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					showCarDetails(newValue);
		});
		
		// Wrap the car ObservableList in a FilteredList (initially display all data).
		FilteredList<Car> filteredCarData =
				new FilteredList<Car>(Car.carData, p -> true);
		
		// Set the filter predicate whenever the car filter changes.
		carSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredCarData.setPredicate(car -> {
				// If search text is empty, display all cars.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				} // end if
				
				// Compare all fields of every car with search text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (car.getLicencePlate().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Search matches licence plate.
				} // end if
				else if (car.getCarCompany().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Search matches company.
				} // end else if
				else if (car.getCarModel().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Search matches model.
				} // end else if
				else if (car.getCarColor().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Search matches color.
				} // end else if
				else if (car.getCarInfo().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Search matches info.
				} // end else if
				
				return false; // Does not match.
			}); // end of setPredicate
		}); // end of addListener
		
		
		// Wrap the car FilteredList in a SortedList.
		SortedList<Car> sortedCarData = new SortedList<Car>(filteredCarData);
		
		
		// Bind the car SortedList comparator to the car TableView comparator.
		sortedCarData.comparatorProperty().bind(carTable.comparatorProperty());
		
		
		// Add sorted(and filtered) data to the car table.
		carTable.setItems(sortedCarData);
		
		
		// set a listener to the car table to listen for double clicks
		carTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					if (carTable.getSelectionModel().getSelectedItem() != null) {
						handleEditCar();
					} // end inner if
				} // end outer if
			} // end of handle method
		}); // end of setOnMousePressed
		
		
		// set a listener to every field to listen for clicks
		carLicencePlateLabel.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					if (carTable.getSelectionModel().getSelectedItem() != null) {
						Constants.clickedLabel = "licencePlateLabel";
						handleEditCar();
					} // end inner if
				} // end outer if
			} // end of handle method
		}); // end of setOnMousePressed
		
		carCompanyLabel.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					if (carTable.getSelectionModel().getSelectedItem() != null) {
						Constants.clickedLabel = "carCompanyLabel";
						handleEditCar();
					} // end inner if
				} // end outer if
			} // end of handle method
		}); // end of setOnMousePressed
		
		carModelLabel.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					if (carTable.getSelectionModel().getSelectedItem() != null) {
						Constants.clickedLabel = "carModelLabel";
						handleEditCar();
					} // end inner if
				} // end outer if
			} // end of handle method
		}); // end of setOnMousePressed
		
		carColorLabel.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					if (carTable.getSelectionModel().getSelectedItem() != null) {
						Constants.clickedLabel = "carColorLabel";
						handleEditCar();
					} // end inner if
				} // end outer if
			} // end of handle method
		}); // end of setOnMousePressed
		
		carInfoLabel.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					if (carTable.getSelectionModel().getSelectedItem() != null) {
						Constants.clickedLabel = "carInfoLabel";
						handleEditCar();
					} // end inner if
				} // end outer if
			} // end of handle method
		}); // end of setOnMousePressed
		
		
		// Initialize transitions of the message
		transitionOut = new TranslateTransition(Duration.millis(1000), messageLabel);
		transitionOut.setFromY(0);
		transitionOut.setToY(50);
		transitionOut.setCycleCount(1);
		transitionOut.setAutoReverse(false);
		
		transitionIn = new TranslateTransition(Duration.millis(1000), messageLabel);
		transitionIn.setDelay(Duration.millis(1000));
		transitionIn.setFromY(50);
		transitionIn.setToY(0);
		transitionIn.setCycleCount(1);
		transitionIn.setAutoReverse(false);
		
		transitionOut2 = new TranslateTransition(Duration.millis(1000), messageLabel);
		transitionOut2.setDelay(Duration.seconds(10));
		transitionOut2.setFromY(0);
		transitionOut2.setToY(50);
		transitionOut2.setCycleCount(1);
		transitionOut2.setAutoReverse(false);
		
		File databaseFolder = new File("database");
		if (!databaseFolder.exists())
			databaseFolder.mkdir();
		
//		File customersFolder = new File("database/Αυτοκίνητα");
//		if (!customersFolder.exists())
//			customersFolder.mkdir();
		
		
		// create a folder for each customer if does not exist
//		for (Car car : Car.carData) {
//			File carFolder = new File("database/Αυτοκίνητα/" + car.getLicencePlate());
//			if (!carFolder.exists()) {
//				carFolder.mkdir();
//			} // end if
//		} // end for
		
		// Set a welcoming message label.
		changeMessageLabelText("Καλώς ήρθατε!");
		
	} // end of initialize method
	
	

} // end of MainWindowController class
