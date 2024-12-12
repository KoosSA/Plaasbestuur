package koossa.plaasbestuur.fxml;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import koossa.plaasbestuur.utils.ILocationsManager;

public class LocationsEditViewController {
	
	@FXML
	ListView<String> locations;
	
	private static LocationsEditViewController instance;
	private static ILocationsManager locManager;
	
	public void initialize() {
		LocationsEditViewController.instance = this;
	}
	
	public void onAdd() {
		TextInputDialog tid = new TextInputDialog();
		Optional<String> loc = tid.showAndWait();
		if (loc.isPresent()) {
			locManager.addLocation(loc.get());
			instance.locations.getItems().add(loc.get());
		}
	}
	
	public void onRemove() {
		String loc = locations.getSelectionModel().getSelectedItem();
		if (loc != null) {
			if (locManager.removeLocation(loc)) {
				instance.locations.getItems().remove(loc);
			};
		}
	}
	
	public void onEdit() {
		String locToChange = locations.getSelectionModel().getSelectedItem();
		if (locToChange != null) {
			TextInputDialog tid = new TextInputDialog();
			tid.setHeaderText("Edit location");
			tid.setContentText("Change " + locToChange + " to:");
			Optional<String> loc = tid.showAndWait();
			if (loc.get() != null) {
				locations.getItems().remove(locToChange);
				locations.getItems().add(loc.get());
				locManager.renameLocation(locToChange, loc.get());
				RainfallViewController.getInstance().changeLocationName(locToChange, loc.get());
			}
		}
	}
	
	public static LocationsEditViewController getInstance() {
		return instance;
	}
	
	public static void setLocManager(ILocationsManager locManager) {
		LocationsEditViewController.locManager = locManager;
		instance.locations.getItems().clear();
		instance.locations.getItems().addAll(locManager.getLocations());
	}

}
