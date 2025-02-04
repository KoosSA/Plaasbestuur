package koossa.plaasbestuur.fxml.livestock;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import koossa.plaasbestuur.data.livestock.LivestockEntry;
import koossa.plaasbestuur.data.livestock.LivestockTypes;
import koossa.plaasbestuur.data.utils.UserData;

public class LivestockNewEntryViewController {
	
	@FXML
	ChoiceBox<String> type_cb;
	@FXML
	ChoiceBox<String> location_cb;
	@FXML
	ChoiceBox<String> mother_cb;
	@FXML
	ChoiceBox<String> gender_cb;
	@FXML
	ChoiceBox<String> race_cb;
	@FXML
	DatePicker birthDate_dp;
	@FXML
	TextField brand_tf;
	
	private ObservableList<String> locations;
	private ObservableList<String> types;
	private ObservableList<String> animalList;
	private ObservableList<String> races;
	private ObservableList<String> genders;
	private LivestockTypes type;
	public static LivestockNewEntryViewController instance;
	
	public void initialize() {
		instance = this;
		animalList = FXCollections.observableArrayList();
		mother_cb.setItems(animalList);
		races = FXCollections.observableArrayList();
		race_cb.setItems(races);
		genders = FXCollections.observableArrayList();
		gender_cb.setItems(genders);
		locations = FXCollections.observableArrayList();
		locations.addAll(UserData.getLivestockData().getLocations());
		location_cb.setItems(locations);
		birthDate_dp.setValue(LocalDate.now());
		types = FXCollections.observableArrayList();
		type_cb.setDisable(true);
//		for (int i = 0; i < LivestockTypes.values().length; i++) {
//			types.add(LivestockTypes.values()[i].getName());
//		}
//		type_cb.setItems(types);
//		type_cb.setOnAction(action -> {
//			animalList.clear();
//			races.clear();
//			genders.clear();
//			for (int i = 0; i < LivestockTypes.values().length; i++) {
//				if (LivestockTypes.values()[i].getName().equals(type_cb.getValue())) {
//					type = LivestockTypes.values()[i];
//					UserData.getLivestockData().getAllAnimalsOfType(type).forEach(a -> {
//						animalList.add(a.getBrand());
//					});
//					races.addAll(type.getUtilData().getRaces());
//					genders.addAll(type.getUtilData().getGenders());
//					break;
//				}
//			}
//		});
	}
	
	public static void setType(LivestockTypes type) {
		instance.type = type;
		instance.type_cb.setValue(type.getName());
		instance.animalList.clear();
		instance.races.clear();
		instance.genders.clear();
		UserData.getLivestockData().getAllAnimalsOfType(type).forEach(a -> {
			instance.animalList.add(a.getBrand());
		});
		instance.races.addAll(type.getUtilData().getRaces());
		instance.genders.addAll(type.getUtilData().getGenders());
	}

	public void onCancel() {
		((Stage) gender_cb.getScene().getWindow()).close();
	}
	
	public void onOk() {
		if (isValuesPresent()) {
			if (isValuesValid(type) && type != null) {
				LivestockEntry newEntry = new LivestockEntry(birthDate_dp.getValue(), true, brand_tf.getText(), gender_cb.getValue(), race_cb.getValue(), type, location_cb.getValue());
				LivestockTypeViewController.addNewEntry(newEntry);
				((Stage) gender_cb.getScene().getWindow()).close();
			}
		}
	}

	private boolean isValuesValid(LivestockTypes type) {
		List<LivestockEntry> ofType = UserData.getLivestockData().getAllAnimalsOfType(type);
		for (int i = 0; i < ofType.size(); i++) {
			if (ofType.get(i).getBrand().equals(brand_tf.getText().trim().toLowerCase())) {
				System.err.println("Animal already exist: " + brand_tf.getText() + " as a " + type_cb.getValue());
				return false;
			}
		}
		return true;
	}

	private boolean isValuesPresent() {
		if (type_cb.getValue() == null) {
			return false;
		}
		if (location_cb.getValue() == null) {
			return false;
		}
		if (mother_cb.getValue() == null) {
			return false;
		}
		if (gender_cb.getValue() == null) {
			return false;
		}
		if (race_cb.getValue() == null) {
			return false;
		}
		if (brand_tf.getText().toLowerCase().trim().length() <= 0) {
			return false;
		}
		if (birthDate_dp.getValue() == null) {
			return false;
		}
		return true;
	}
	
	
	
}
