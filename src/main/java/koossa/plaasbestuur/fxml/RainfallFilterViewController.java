package koossa.plaasbestuur.fxml;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import koossa.plaasbestuur.data.utils.UserData;

public class RainfallFilterViewController {
	
	@FXML
	DatePicker filter_startDate;
	@FXML
	DatePicker filter_endDate;
	@FXML
	ChoiceBox<String> filter_location;
	
	private static RainfallFilterViewController instance;
	private Stage stage;
	
	public void initialize() {
		RainfallFilterViewController.instance = this;
		try {
			filter_location.getItems().addAll(UserData.getRainfallData().getLocations());
		} catch(Exception e) {}
		filter_endDate.setValue(LocalDate.now());
		filter_startDate.setValue(LocalDate.of(LocalDate.now().getYear() - 1 , LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
	}
	
	public void onFilter() {
		if (filter_startDate.getValue() != null && filter_endDate.getValue() != null && filter_location.getValue() != null) {
			RainfallViewController.getInstance().setFilterData(filter_startDate.getValue(), filter_endDate.getValue(),
					filter_location.getValue());
			RainfallViewController.getInstance().onFilter();
			stage.close();
		}
	}
	
	public void onCancel() {
		stage.close();
	}
	
	public static RainfallFilterViewController getInstance() {
		return instance;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	protected void onEditLocations() {
		filter_location.getItems().clear();
		filter_location.getItems().addAll(UserData.getRainfallData().getLocations());
	}

}
