package koossa.plaasbestuur.fxml;

import java.util.prefs.BackingStoreException;

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
	
	public void onDeleteAccount() {
		try {
			PlaasBestuur.getPreferences().clear();
			PlaasBestuur.getPreferences().flush();
			PlaasBestuur.switchView(FxmlViewNames.LOGIN_VIEW);
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
