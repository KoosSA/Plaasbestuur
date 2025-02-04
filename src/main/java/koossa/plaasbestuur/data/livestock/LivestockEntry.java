package koossa.plaasbestuur.data.livestock;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class LivestockEntry implements Serializable {
	
	private static final long serialVersionUID = 649525297284854248L;
	
	
	private LocalDate birthDate;
	private LocalDate deathDate;
	private boolean alive;
	private String brand;
	private String gender;
	private String race;
	private long id;
	private LivestockTypes animalType;
	private String location;
	private Map<LocalDate, String> injections;
	private List<Integer> childrenIds;
	
	
	
	public LivestockEntry(LocalDate birthDate, boolean alive, String brand, String gender,
			String race, LivestockTypes animalType, String location) {
		this.birthDate = birthDate;
		this.alive = alive;
		this.brand = brand;
		this.gender = gender;
		this.race = race;
		this.animalType = animalType;
		this.location = location;
		this.id = LivestockManager.generateNewId();
	}

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
	
	public String getGender() {
		return gender;
	}
	
	public long getId() {
		return id;
	}
	
}
