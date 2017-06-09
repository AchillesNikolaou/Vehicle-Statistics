// Car.java
package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class for Car.
 */
public class Car {
	
	private SimpleIntegerProperty carID; // car unique key
	
	// fields
	private SimpleStringProperty licencePlate;
	private SimpleStringProperty carCompany;
	private SimpleStringProperty carModel;
	private SimpleStringProperty carColor;
	private SimpleStringProperty carInfo;
	
	
	/** The data as an observable list of Cars. */
	public static ObservableList<Car> carData = FXCollections.observableArrayList();
	
	
	/** Default Constructor. */
	public Car() {
		this(null, null, null, null, null);
	} // end of Car default constructor
	
	
	/** Car Constructor for new cars. */
	public Car(String licencePlate, String carCompany, String carModel,
			String carColor, String carInfo) {
		
		this.carID = new SimpleIntegerProperty(generateCarID());
		this.licencePlate = new SimpleStringProperty(licencePlate);
		this.carCompany = new SimpleStringProperty(carCompany);
		this.carModel = new SimpleStringProperty(carModel);
		this.carColor = new SimpleStringProperty(carColor);
		this.carInfo = new SimpleStringProperty(carInfo);
		
	} // end of Car constructor
	
	
	/** Car Constructor for existing cars.
	 * (To be used for updating a car.) */
	public Car(int carID, String licencePlate, String carCompany, String carModel,
			String carColor, String carInfo) {
		this.carID = new SimpleIntegerProperty(carID);
		this.licencePlate = new SimpleStringProperty(licencePlate);
		this.carCompany = new SimpleStringProperty(carCompany);
		this.carModel = new SimpleStringProperty(carModel);
		this.carColor = new SimpleStringProperty(carColor);
		this.carInfo = new SimpleStringProperty(carInfo);
		
	} // end of Car constructor
	
	
	
	// get methods
	public Integer getCarID() { return carID.get(); }	
	public String getLicencePlate() { return licencePlate.get(); }
	public String getCarCompany() { return carCompany.get(); }
	public String getCarModel() { return carModel.get(); }
	public String getCarColor() {return carColor.get(); }
	public String getCarInfo() {return carInfo.get(); }
	
	
	// set methods
	public void setCarID(Integer carID) { this.carID.set(carID); }
	public void setLicencePlate(String licencePlate) { this.licencePlate.set(licencePlate); }
	public void setCarCompany(String carCompany) { this.carCompany.set(carCompany); }
	public void setCarModel(String carModel) { this.carModel.set(carModel); }
	public void setCarColor(String carColor) { this.carColor.set(carColor); }
	public void setCarInfo(String carInfo) { this.carInfo.set(carInfo); }
	
	
	private Integer generateCarID() {
		int carID = 1;
		
		// check if there are no records
		if (carData.size() == 0) {
			return carID;
		} // end if
		
		// there are records, now find the highest ID
		for (int i = 0; i < carData.size(); i++) {
			if (carData.get(i).getCarID() > carID) {
				// get the highest ID
				carID = carData.get(i).getCarID();
			} // end if
		} // end for
		
		// return the maximum incremented by 1
		return carID += 1;
	} // end of generateCarID method
	
	
	public static Car getCarByID(int carID) {
		for (int i = 0; i < carData.size(); i++) {
			if (carID == carData.get(i).getCarID()) {
				return carData.get(i);
			} // end if
		} // end for
		
		return null;
	} // end of getCarByID method
	
	
	public static void updateCar(Car carOldValues, Car carNewValues) {
		for (int i = 0; i < carData.size(); i++) {
			if (carData.get(i).getCarID().intValue() == carOldValues.getCarID().intValue()) {
				carData.set(i, carNewValues);
			} // end if
		} // end for
	} // end of updateCar method
	
	
	public static void deleteCar(int carID) {
		for (int i = 0; i < carData.size(); i++) {
			if (carID == carData.get(i).getCarID()) {
				carData.remove(i);
			} // end if
		} // end for
	} // end of deleteCar method
	
	
	public static boolean entryAlredyExists(int carID, String licencePlate) {
		for (Car car : carData) {
			if (car.getCarID() != carID) {
				if (car.getLicencePlate().toLowerCase().equals(licencePlate.toLowerCase())) {
					return true;
				} // end if
			} // end if
		} // end for
		return false;
	} // end of checkIfEntryAlredyExists method

	
} // end of Car class
