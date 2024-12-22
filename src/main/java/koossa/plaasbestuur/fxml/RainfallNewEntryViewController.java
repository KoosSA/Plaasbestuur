package koossa.plaasbestuur.fxml;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import koossa.plaasbestuur.data.rain.RainEntry;
import koossa.plaasbestuur.data.utils.UserData;

public class RainfallNewEntryViewController {
	
	@FXML
	DatePicker entry_date;
	@FXML
	ChoiceBox<String> entry_location;
	@FXML
	TextField entry_amount;
	
	private static RainfallNewEntryViewController instance;
	private Stage stage;
	
	public void initialize() {
		RainfallNewEntryViewController.instance = this;
		try {
			entry_location.getItems().addAll(UserData.getRainfallData().getLocations());
		} catch (Exception e) {}
		entry_date.setValue(LocalDate.now());
	}
	
	public void onOk() {
		try {
			if (entry_date.getValue() != null && entry_location.getValue() != null && entry_location.getValue().trim().length() > 0 && Double.parseDouble(entry_amount.getText()) > 0) {
				RainEntry entry = new RainEntry(entry_date.getValue(), entry_location.getValue(),
						Double.parseDouble(entry_amount.getText()));
				UserData.getRainfallData().addEntry(entry);
				RainfallViewController.getInstance().addEntry(entry_date.getValue(), entry_location.getValue(),
						entry_amount.getText());
				stage.close();
			}
		} catch (NumberFormatException e) {
			System.err.println("Not a number entered");
		}
	}
	
	public void onCancel() {
		stage.close();
	}
	
	public static RainfallNewEntryViewController getInstance() {
		return instance;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	protected void onEditLocations() {
		entry_location.getItems().clear();
		entry_location.getItems().addAll(UserData.getRainfallData().getLocations());
	}
		

}
