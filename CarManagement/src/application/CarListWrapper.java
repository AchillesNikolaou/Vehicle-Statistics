// CarListWrapper.java
package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Helper class to wrap a list of cars. This is used for saving the
 * list of cars to XML.
 */
@XmlRootElement(name = "cars")
public class CarListWrapper {
	
	private List<Car> cars;
	
	
	@XmlElement(name = "car")
	public List<Car> getCars() {
		return cars;
	} // end of getCars method
	
	
	public void setCars(List<Car> cars) {
		this.cars = cars;
	} // end of setCars method

} // end class CarListWrapper
