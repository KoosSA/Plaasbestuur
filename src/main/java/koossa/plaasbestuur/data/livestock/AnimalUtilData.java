package koossa.plaasbestuur.data.livestock;

import java.util.ArrayList;
import java.util.List;

public class AnimalUtilData {
	
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
	
	public List<String> getRaces() {
		return races;
	}
	
	public List<String> getGenders() {
		return genders;
	}

}
