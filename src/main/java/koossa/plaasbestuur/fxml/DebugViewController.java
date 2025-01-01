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
		if (PlaasBestuur.getStorage().getPrivateStorage().isPresent()) {
			private_lbl.setText(PlaasBestuur.getStorage().getPrivateStorage().get().getAbsolutePath());
		} else {
			private_lbl.setText("Not available");
		}
		if (PlaasBestuur.getStorage().getPublicStorage("Plaasbestuur").isPresent()) {
			public_lbl.setText(PlaasBestuur.getStorage().getPublicStorage("Plaasbestuur").get().getAbsolutePath());
		} else {
			public_lbl.setText("Not available");
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
