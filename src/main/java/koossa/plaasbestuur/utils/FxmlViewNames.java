package koossa.plaasbestuur.utils;

public enum FxmlViewNames {
	
	MAIN_APP_VIEW("mainView.fxml"),
	DEBUG_VIEW("debugView.fxml"),
	LOGIN_VIEW("loginView.fxml"),
	REGISTER_VIEW("registerView.fxml"),
	RAINFALL_VIEW("rainfall/rainfallView.fxml"),
	RAINFALL_CHART_VIEW("rainfall/rainfallChartView.fxml"),
	RAINFALL_NEW_ENTRY_VIEW("rainfall/rainfallNewEntryView.fxml"),
	RAINFALL_FILTER_VIEW("rainfall/rainfallFilterView.fxml"),
	RAINFALL_STATS_VIEW("rainfall/rainfallStatsView.fxml"),
	LOCATIONS_EDIT_VIEW("locationsEditView.fxml");
	

	FxmlViewNames(String string) {
		data = string;
	}
	
	String data;
	
	public String getData() {
		return data;
	}
	
	

}
