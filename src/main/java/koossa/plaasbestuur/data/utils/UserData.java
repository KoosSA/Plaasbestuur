package koossa.plaasbestuur.data.utils;

import koossa.plaasbestuur.data.livestock.LivestockManager;
import koossa.plaasbestuur.data.rain.Rainfall;

public class UserData {
	
	private static Rainfall rainfallData = new Rainfall();
	private static LivestockManager livestockData = new LivestockManager();
	
	public static Rainfall getRainfallData() {
		return rainfallData;
	}
	
	public static LivestockManager getLivestockData() {
		return livestockData;
	}

}
