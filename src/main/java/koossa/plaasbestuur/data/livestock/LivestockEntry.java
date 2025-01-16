package koossa.plaasbestuur.data.livestock;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class LivestockEntry {
	
	private LocalDate birthDate;
	private LocalDate deathDate;
	private boolean alive;
	private String brand;
	private String gender;
	private String race;
	private int id;
	private LivestockTypes animalType;
	private String location;
	private Map<LocalDate, String> injections;
	private List<Integer> childrenIds;
	
	public LivestockTypes getAnimalType() {
		return animalType;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public int getAgeMonths() {
		return (int) birthDate.until(LocalDate.now()).toTotalMonths();
	}
	
	public int getAgeYears() {
		return birthDate.until(LocalDate.now()).getYears();
	}
	
	public String getBrand() {
		return brand;
	}
	
	public String getRace() {
		return race;
	}
	
	
}
