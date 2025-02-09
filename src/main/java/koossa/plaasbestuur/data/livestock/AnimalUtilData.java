package koossa.plaasbestuur.data.livestock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnimalUtilData implements Serializable {
	
	private static final long serialVersionUID = -706164353863464547L;
	private List<String> races = new ArrayList<String>();
	private List<String> genders = new ArrayList<String>();
	
	public void addRace(String race) {
		if (!races.contains(race)) {
			races.add(race);
		}
	}
	
	public void addGender(String gender) {
		if (!genders.contains(gender)) {
			genders.add(gender);
		}
	}
	
	public boolean removeRace(String race) {
		return races.remove(race);
	}
	
	public boolean removeGender(String gender) {
		return genders.remove(gender);
	}
	
	public List<String> getRaces() {
		return races;
	}
	
	public List<String> getGenders() {
		return genders;
	}
	
	public void add(String currentModifier, String entry) {
		switch (currentModifier) {
		case "gender":
			addGender(entry);
			break;
		case "race":
			addRace(entry);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentModifier);
		}
	}
	
	public boolean remove(String currentModifier, String entry) {
		switch (currentModifier) {
		case "gender":
			return removeGender(entry);
		case "race":
			return removeRace(entry);
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentModifier);
		}
	}

	public List<String> get(String currentModifier) {
		switch (currentModifier) {
		case "gender":
			return getGenders();
		case "race":
			return getRaces();
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentModifier);
		}
	}

}
