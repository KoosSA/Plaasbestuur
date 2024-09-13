package koossa.plaasbestuur.utils;

public enum FxmlViewNames {
	
	MAIN_APP_VIEW("mainView.fxml"),
	DEBUG_VIEW("debugView.fxml"),
	LOGIN_VIEW("loginView.fxml"),
	REGISTER_VIEW("registerView.fxml"),
	RAINFALL_VIEW("rainfallView.fxml");

	FxmlViewNames(String string) {
		data = string;
	}
	
	String data;
	
	public String getData() {
		return data;
	}
	
	

}
