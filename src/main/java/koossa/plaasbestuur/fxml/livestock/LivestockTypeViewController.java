package koossa.plaasbestuur.fxml.livestock;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.data.livestock.LivestockEntry;
import koossa.plaasbestuur.data.livestock.LivestockTypes;
import koossa.plaasbestuur.data.utils.UserData;
import koossa.plaasbestuur.fxml.livestock.util.ListEntryElement;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class LivestockTypeViewController {

	private static LivestockTypeViewController instance;
	private LivestockTypes type;
	private ObservableList<String> locations;
	private ObservableList<ListEntryElement> entries = FXCollections.observableArrayList();
	@FXML
	Label title_lbl;
	@FXML
	ChoiceBox<String> location_cbox;
	@FXML
	ListView<ListEntryElement> entry_container;

	public void initialize() {
		instance = this;
		locations = FXCollections.observableArrayList();
		locations.addAll(UserData.getLivestockData().getLocations());
		location_cbox.setItems(locations);
		location_cbox.setOnAction(event -> {
			if (event.getEventType().equals(ActionEvent.ACTION)) {
				onChangeLocation(location_cbox.getValue());
			}
		});
		entry_container.setItems(entries);
	}

	public void updateEntries(String location) {
		entries.clear();
		if (location == null) {
			UserData.getLivestockData().getAllAnimalsOfType(type).forEach(entry -> {
				entries.add(new ListEntryElement(entry));
			});
		} else {
			UserData.getLivestockData().getAllAnimalsOfTypeAtLocation(type, location).forEach(entry -> {
				entries.add(new ListEntryElement(entry));
			});
		}
	}

	public void onAdd() {
		LivestockEntry entry = new LivestockEntry(LocalDate.now(), true, "test", "Koei", "Afrikaner", type, "Hoekblok");
		UserData.getLivestockData().addAnimalEntry(entry);
		entries.add(new ListEntryElement(entry));
		
		
		
		// TODO Auto-generated method stub
		UserData.getLivestockData().save();
	}

	public void onDeleteEntry() {
		// TODO Auto-generated method stub
		UserData.getLivestockData().save();
	}

	public void onFilter() {
		// TODO Auto-generated method stub

	}

	private void onChangeLocation(String newLocation) {
		updateEntries(newLocation);
	}

	public void onLivestock() {
		PlaasBestuur.switchView(FxmlViewNames.LIVESTOCK_VIEW);
	}

	public static void setAnimalType(LivestockTypes type) {
		instance.type = type;
		instance.title_lbl.setText(type.getName());
	}

	public static void updateLocations() {
		if (instance != null) {
			instance.locations.clear();
			instance.locations.addAll(UserData.getLivestockData().getLocations());
		}
	}
	
	public static void updateEntriesStatic(String location) {
		instance.updateEntries(location);
	}
}
