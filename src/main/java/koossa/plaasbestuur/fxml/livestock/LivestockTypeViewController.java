package koossa.plaasbestuur.fxml.livestock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.data.livestock.LivestockEntry;
import koossa.plaasbestuur.data.livestock.LivestockTypes;
import koossa.plaasbestuur.data.utils.UserData;
import koossa.plaasbestuur.fxml.livestock.util.ListEntryElement;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Screen;

public class LivestockTypeViewController {

	private static LivestockTypeViewController instance;
	private LivestockTypes type;
	private ObservableList<String> locations;
	private ObservableList<ListEntryElement> entries = FXCollections.observableArrayList();
	private Scene newEntryScene;
	@FXML
	Label title_lbl;
	@FXML
	ChoiceBox<String> location_cbox;
	@FXML
	ListView<ListEntryElement> entry_container;

	public void initialize() {
		instance = this;
		newEntryScene = FxmlViewManager.getScene(FxmlViewNames.LIVESTOCK_NEW_ENTRY_VIEW);
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
	
	public void onEditTypeGenders() {
		//FIXME Add gender editor
	}
	
	public void onEditTypeRaces() {
		//FIXME Add race editor
	}

	public void onAdd() {
		Stage pop = new Stage();
		pop.setScene(newEntryScene);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.initOwner(title_lbl.getScene().getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.centerOnScreen();
		pop.requestFocus();
		pop.toFront();
		Screen.fitToScreenIfMobile(pop);
		LivestockNewEntryViewController.setType(type);
		pop.showAndWait();
	}

	public void onDeleteEntry() {
		ListEntryElement toremove = entry_container.getSelectionModel().getSelectedItem();
		if (toremove != null) {
			Alert deletionAlert = new Alert(AlertType.CONFIRMATION);
			deletionAlert.setHeaderText(FxmlViewManager.getLanguageBundle().getString("confirmDelete") + toremove.getEntry().getRace() + " @ " + toremove.getEntry().getLocation() + ": " + toremove.getEntry().getBrand());
			deletionAlert.setContentText(FxmlViewManager.getLanguageBundle().getString("livestockEntryDeleteConfirm"));
			Screen.fitToWidthIfMobile(deletionAlert.getDialogPane().getScene().getWindow());
			if (deletionAlert.showAndWait().get().getButtonData() == ButtonData.OK_DONE) {
				
				if (UserData.getLivestockData().removeLivestockEntry(toremove.getEntry())) {
					int index = entry_container.getSelectionModel().getSelectedIndex();
					if (index != -1) {
						entries.remove(index);
						entry_container.getSelectionModel().clearSelection();
					}
					onFilter();
				}
				
				UserData.getLivestockData().save();
			}
		}
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
	
	protected static void addNewEntry(LivestockEntry newEntry) {
		UserData.getLivestockData().addAnimalEntry(newEntry);
		instance.entries.add(new ListEntryElement(newEntry));
		UserData.getLivestockData().save();
	}
}
