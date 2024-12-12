package koossa.plaasbestuur.utils;

import java.util.List;

public interface ILocationsManager {
	
	boolean addLocation(String location);
	
	boolean removeLocation(String location);
	
	List<String> getLocations();

	void renameLocation(String locToChange, String newName);

}
