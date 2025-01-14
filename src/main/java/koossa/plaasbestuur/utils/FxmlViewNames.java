package koossa.plaasbestuur.utils;

public enum FxmlViewNames {
	
	MAIN_APP_VIEW("mainView.fxml"),
	DEBUG_VIEW("debugView.fxml"),
	LOGIN_VIEW("loginView.fxml"),
	LOCATIONS_EDIT_VIEW("locationsEditView.fxml"),
	REGISTER_VIEW("registerView.fxml"),
	//Rainfall
	RAINFALL_VIEW("rainfall/rainfallView.fxml"),
	RAINFALL_CHART_VIEW("rainfall/rainfallChartView.fxml"),
	RAINFALL_NEW_ENTRY_VIEW("rainfall/rainfallNewEntryView.fxml"),
	RAINFALL_FILTER_VIEW("rainfall/rainfallFilterView.fxml"),
	RAINFALL_STATS_VIEW("rainfall/rainfallStatsView.fxml"),
	//Livestock
	LIVESTOCK_TYPE_VIEW("livestock/livestockTypeView.fxml"),
	LIVESTOCK_VIEW("livestock/livestockView.fxml");
	;
	

	FxmlViewNames(String string) {
		data = string;
	}
	
	String data;
	
	public String getData() {
		return data;
	}
	
	

}
