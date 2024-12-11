package koossa.plaasbestuur.data.rain;

import java.util.ArrayList;
import java.util.List;

import koossa.plaasbestuur.utils.ILocationsManager;

public class Rainfall implements ILocationsManager {
	
	private List<String> locations = new ArrayList<String>();
	
	@Override
	public boolean addLocation(String location) {
		if (location != null) {
			if (!locations.contains(location) && location.length() > 0) {
				locations.add(location);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean removeLocation(String location) {
		if (location != null) {
			if (locations.contains(location) && location.length() > 0) {
				locations.remove(location);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<String> getLocations() {
		return locations;
	}
	
	
	

}
