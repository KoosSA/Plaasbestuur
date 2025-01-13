package koossa.plaasbestuur.fxml;

import java.util.prefs.BackingStoreException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Language;
import koossa.plaasbestuur.utils.Storage;

public class DebugViewController {
	
	@FXML
	ChoiceBox<FxmlViewNames> viewChooser;
	@FXML
	ChoiceBox<Language> languageChooser;
	@FXML
	Label private_lbl;
	@FXML
	Label public_lbl;
	
	public void initialize() {
		viewChooser.setValue(PlaasBestuur.getCurrentView());
		viewChooser.setItems(FXCollections.observableArrayList(FxmlViewNames.values()));
		languageChooser.setItems(FXCollections.observableArrayList(Language.values()));
		languageChooser.setValue(FxmlViewManager.getCurrentLanguage());
		
		private_lbl.setText(Storage.getPrivateFolder().getAbsolutePath());
		
		if (Storage.isPublicStorageAvailable()) {
			public_lbl.setText(Storage.getPublicFolder().getAbsolutePath());
		} else {
			public_lbl.setText("Not available. Using private storage");
		}
		
		
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
			e.printStackTrace();
		}
	}

}
