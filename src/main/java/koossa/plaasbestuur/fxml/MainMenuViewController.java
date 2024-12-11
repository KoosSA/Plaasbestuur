package koossa.plaasbestuur.fxml;

import javafx.application.Platform;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class MainMenuViewController {
	
	public void onQuit() {
		Platform.exit();
	}
	
	public void onSettings() {
		PlaasBestuur.switchView(FxmlViewNames.DEBUG_VIEW);
	}
	
	public void onCattle() {
		
	}
	
	public void onRainfall() {
		PlaasBestuur.switchView(FxmlViewNames.RAINFALL_VIEW);
	}

}
