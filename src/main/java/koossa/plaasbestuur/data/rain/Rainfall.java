package koossa.plaasbestuur.data.rain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import koossa.plaasbestuur.data.utils.Savable;
import koossa.plaasbestuur.utils.ILocationsManager;

public class Rainfall extends Savable<Object> implements ILocationsManager {
	
	private static final long serialVersionUID = -3051198349194122001L;
	private List<String> locations = new ArrayList<String>();
	private Map<String, List<RainEntry>> rainEntries = new HashMap<String, List<RainEntry>>();
	
	@Override
	protected void onLoad(Object loaded) {
		this.locations = getClass().cast(loaded).getLocations();
		this.rainEntries = getClass().cast(loaded).getRainEntries();
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
	
	public Map<String, List<RainEntry>> getRainEntries() {
		return rainEntries;
	}
	
	public List<RainEntry> getRainEntriesByLocation(String location) {
		return rainEntries.getOrDefault(location, new ArrayList<RainEntry>());
	}

	public void addEntry(RainEntry rainEntry) {
		List<RainEntry> list = getRainEntriesByLocation(rainEntry.getLocation());
		list.add(rainEntry);
		rainEntries.put(rainEntry.getLocation(), list);
	}

	@Override
	public void renameLocation(String locToChange, String newName) {
		locations.remove(locToChange);
		locations.add(newName);
		List<RainEntry> list = getRainEntriesByLocation(locToChange);
		rainEntries.remove(locToChange);
		list.forEach(e-> {
			e.setLoacation(newName);
		});
		rainEntries.put(newName, list);
	}

	
	
	
	

}
