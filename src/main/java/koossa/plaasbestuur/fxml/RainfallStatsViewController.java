package koossa.plaasbestuur.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RainfallStatsViewController {
	
	@FXML
	Label stats_averageFiltered;
	@FXML
	Label stats_totalFiltered;
	
	private static RainfallStatsViewController instance;
	
	public void initialize() {
		RainfallStatsViewController.instance = this;
	}
	
	public void onClose() {
		((Stage) stats_averageFiltered.getScene().getWindow()).close();
	}
	
	private void updateValuesInternal(double ave, double total) {
		stats_totalFiltered.setText(String.valueOf(Math.round(ave * 100.0) / 100.0));
		stats_averageFiltered.setText(String.valueOf(Math.round(ave * 100.0)/100.0));
	}
	
	public static void updateValues(double ave, double total) {
		instance.updateValuesInternal(ave, total);
	}

}
