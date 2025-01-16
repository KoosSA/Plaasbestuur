package koossa.plaasbestuur.data.livestock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import koossa.plaasbestuur.data.utils.Savable;
import koossa.plaasbestuur.utils.ILocationsManager;

public class LivestockManager extends Savable<Object> implements ILocationsManager {
	
	private static final long serialVersionUID = 5715700442463198018L;
	//Stores all livestock in format: typeOfAnimal -> Location -> List of animals
	private Map<LivestockTypes, Map<String, List<LivestockEntry>>> allLivestock = new HashMap<LivestockTypes, Map<String,List<LivestockEntry>>>();
	private List<String> locations = new ArrayList<String>();
	
	public Map<String, List<LivestockEntry>> getAllLocationsWithAnimalType(LivestockTypes animalType) {
		return allLivestock.getOrDefault(animalType, new HashMap<String, List<LivestockEntry>>());
	}
	
	public List<LivestockEntry> getAllAnimalsOfType(LivestockTypes animalType) {
		List<LivestockEntry> list = new ArrayList<LivestockEntry>();
		allLivestock.get(animalType).forEach((loc, am) -> {
			list.addAll(am);
		});
		return list;
	}
	
	public List<LivestockEntry> getAllAnimalsAtLocation(String location) {
		List<LivestockEntry> list = new ArrayList<LivestockEntry>();
		allLivestock.forEach((type, mp) -> {
			mp.forEach((loc, al) -> {
				if (loc.equals(loc)) {
					list.addAll(al);
				}
			});
		});
		return list;
	}

	public Map<LivestockTypes, Map<String, List<LivestockEntry>>> getAllLivestock() {
		return allLivestock;
	}
	
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

	@Override
	public void renameLocation(String locToChange, String newName) {
		locations.remove(locToChange);
		locations.add(newName);
		List<LivestockEntry> list = getAllAnimalsAtLocation(locToChange);
		
		allLivestock.forEach((type, mp) -> {
			if (mp.remove(locToChange) != null) {
				mp.put(newName, new ArrayList<LivestockEntry>());
			}
		});
		list.forEach(an -> {
			an.setLocation(newName);
			allLivestock.get(an.getAnimalType()).get(newName).add(an);
		});
	}

	@Override
	protected void onLoad(Object loaded) {
		this.locations = getClass().cast(loaded).getLocations();
		this.allLivestock = getClass().cast(loaded).getAllLivestock();
	}

	
}
