package koossa.plaasbestuur.fxml;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class MainMenuViewController {
	
	@FXML
	AnchorPane pane;
	
	public void initialize() {
		pane.setBackground(FxmlViewManager.getBackground());
	}
	
	public void onQuit() {
		PlaasBestuur.exit();
	}
	
	public void onSettings() {
		PlaasBestuur.switchView(FxmlViewNames.DEBUG_VIEW);
	}
	
	public void onLiveStock() {
		PlaasBestuur.switchView(FxmlViewNames.LIVESTOCK_VIEW);
	}
	
	public void onRainfall() {
		PlaasBestuur.switchView(FxmlViewNames.RAINFALL_VIEW);
	}

}
