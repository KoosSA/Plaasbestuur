package koossa.plaasbestuur.data.utils;

import koossa.plaasbestuur.data.rain.Rainfall;

public class UserData {
	
	private static Rainfall rainfallData = new Rainfall();
	
	public static Rainfall getRainfallData() {
		return rainfallData;
	}

}
