package koossa.plaasbestuur.fxml.livestock;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import koossa.plaasbestuur.data.livestock.LivestockTypes;
import koossa.plaasbestuur.data.utils.UserData;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.Screen;

public class AdditionalDataEditViewController {
	
	private LivestockTypes currentType;
	private String currentModifier;
	private ObservableList<String> entryList;
	private static AdditionalDataEditViewController instance;
	
	@FXML
	Label titleType_lbl;
	@FXML
	ListView<String> entries;
	
	public void initialize() {
		instance = this;
		entryList = FXCollections.observableArrayList();
		entries.setItems(entryList);
	}
	
	public void onSave() {
		((Stage) titleType_lbl.getScene().getWindow()).close();
	}
	
	public void onEdit() {
		//FIXME implement
	}
	
	public void onRemove() {
		//TODO Translate
		String str = entries.getSelectionModel().getSelectedItem();
		if (str != null) {
			if (UserData.getLivestockData().getAdditionalDataOfType(currentType).get(currentModifier).contains(str)) {
				Alert deletionAlert = new Alert(AlertType.CONFIRMATION);
				deletionAlert.initOwner(titleType_lbl.getScene().getWindow());
				deletionAlert.setHeaderText(FxmlViewManager.getLanguageBundle().getString("confirmDelete") + str);
//				deletionAlert.setContentText(FxmlViewManager.getLanguageBundle().getString("locationDeleteConfirm"));
				Screen.fitToWidthIfMobile(deletionAlert.getDialogPane().getScene().getWindow());
				if (deletionAlert.showAndWait().get().getButtonData() == ButtonData.OK_DONE) {
					if (UserData.getLivestockData().getAdditionalDataOfType(currentType).remove(currentModifier, str)) {
						instance.entries.getItems().remove(str);
					}
				}
			}
		}
	}
	
	public void onAdd() {
		//TODO Translate
		TextInputDialog tid = new TextInputDialog();
		tid.setTitle("Add Data:");
		tid.setHeaderText("Please enter a data to add.");
		tid.setContentText("Data to add:");
		tid.initOwner(titleType_lbl.getScene().getWindow());
		Screen.fitToWidthIfMobile(tid.getDialogPane().getScene().getWindow());
		Optional<String> entry = tid.showAndWait();
		if (entry.isPresent()) {
			if (entry.get().length() > 0) {
				UserData.getLivestockData().getAdditionalDataOfType(currentType).add(currentModifier, entry.get());
				instance.entries.getItems().add(entry.get());
			}
		}
	}
	
	private void onTypeChanged(LivestockTypes newType, String dataToChange) {
		titleType_lbl.setText(currentType.getName() + " <<" + FxmlViewManager.getLanguageBundle().getString(dataToChange) + ">>");
		entryList.clear();
		entryList.addAll(UserData.getLivestockData().getAdditionalDataOfType(newType).get(dataToChange));
		
	}
	
	public static void setCurrentEditorType(LivestockTypes newType, String dataToChange) {
		instance.currentType = newType;
		instance.currentModifier = dataToChange;
		instance.onTypeChanged(newType, dataToChange);
	}

}
