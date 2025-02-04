package koossa.plaasbestuur.data.livestock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import koossa.plaasbestuur.data.utils.Savable;
import koossa.plaasbestuur.utils.ILocationsManager;

public class LivestockManager extends Savable<Object> implements ILocationsManager {
	
	private static final long serialVersionUID = 5715700442463198018L;
	private static long lastId = 0;
	//Stores all livestock in format: typeOfAnimal -> Location -> List of animals
	private Map<LivestockTypes, Map<String, List<LivestockEntry>>> allLivestock = new HashMap<LivestockTypes, Map<String,List<LivestockEntry>>>();
	private List<String> locations = new ArrayList<String>();
	
	public void addAnimalEntry(LivestockEntry entry) {
		validateAnimalType(entry.getAnimalType());
		Map<String, List<LivestockEntry>> m1 = allLivestock.get(entry.getAnimalType());
		if (m1 == null) {
			allLivestock.put(entry.getAnimalType(), new HashMap<String, List<LivestockEntry>>());
			addAnimalEntry(entry);
		} else {
			List<LivestockEntry> list = m1.get(entry.getLocation());
			if (list == null) {
				list = new ArrayList<LivestockEntry>();
				list.add(entry);
				m1.put(entry.getLocation(), list);
				return;
			} else {
				list.add(entry);
			}
		}
	}
	
	public Map<String, List<LivestockEntry>> getAllLocationsWithAnimalType(LivestockTypes animalType) {
		validateAnimalType(animalType);
		return allLivestock.getOrDefault(animalType, new HashMap<String, List<LivestockEntry>>());
	}
	
	public List<LivestockEntry> getAllAnimalsOfTypeAtLocation(LivestockTypes animalType, String location) {
		List<LivestockEntry> list = new ArrayList<LivestockEntry>();
		validateAnimalTypeAtLocation(animalType, location);
		allLivestock.get(animalType).forEach((loc, am) -> {
			if (loc.equals(location)) {
				list.addAll(am);
			}
		});
		return list;
	}

	public List<LivestockEntry> getAllAnimalsOfType(LivestockTypes animalType) {
		List<LivestockEntry> list = new ArrayList<LivestockEntry>();
		validateAnimalType(animalType);
		allLivestock.getOrDefault(animalType, new HashMap<String, List<LivestockEntry>>()).forEach((loc, am) -> {
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

	public static long generateNewId() {
		lastId++;
		return lastId;
	}

	private void validateAnimalType(LivestockTypes animalType) {
		if (!allLivestock.containsKey(animalType)) {
			allLivestock.put(animalType, new HashMap<String, List<LivestockEntry>>());
		}
	}
	
	private void validateAnimalTypeAtLocation(LivestockTypes animalType, String location) {
		validateAnimalType(animalType);
		if (!allLivestock.get(animalType).containsKey(location)) {
			allLivestock.get(animalType).put(location, new ArrayList<LivestockEntry>());
		}
	}

	public boolean removeLivestockEntry(LivestockEntry entry) {
		return allLivestock.get(entry.getAnimalType()).get(entry.getLocation()).remove(entry);
	}
}
