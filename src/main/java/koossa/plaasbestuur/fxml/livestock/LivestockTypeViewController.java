package koossa.plaasbestuur.fxml.livestock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.data.livestock.LivestockTypes;
import koossa.plaasbestuur.data.utils.UserData;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class LivestockTypeViewController {
	
	private static LivestockTypeViewController instance;
	private LivestockTypes type;
	private ObservableList<String> locations;
	@FXML
	Label title_lbl;
	@FXML
	ChoiceBox<String> location_cbox;
	@FXML
	ListView<?> entry_container;
	
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
	}
	
	public void onAdd() {
		// TODO Auto-generated method stub

	}
	
	public void onDeleteEntry() {
		// TODO Auto-generated method stub

	}
	
	public void onFilter() {
		// TODO Auto-generated method stub

	}
	
	private void onChangeLocation(String newLocation) {
		
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
}
