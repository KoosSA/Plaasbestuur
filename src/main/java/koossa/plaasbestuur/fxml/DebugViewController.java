package koossa.plaasbestuur.fxml;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Language;

public class DebugViewController {
	
	@FXML
	ChoiceBox<FxmlViewNames> viewChooser;
	@FXML
	ChoiceBox<Language> languageChooser;
	
	public void initialize() {
		viewChooser.setValue(PlaasBestuur.getCurrentView());
		viewChooser.setItems(FXCollections.observableArrayList(FxmlViewNames.values()));
		languageChooser.setItems(FXCollections.observableArrayList(Language.values()));
		languageChooser.setValue(FxmlViewManager.getCurrentLanguage());
		
	}
	
	public void onSwitchView() {
		PlaasBestuur.switchView(viewChooser.getValue());
	}
	
	public void onSwitchLanguage() {
		PlaasBestuur.setLanguage(languageChooser.getValue());
	}

}
