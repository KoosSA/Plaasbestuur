package koossa.plaasbestuur.fxml;

import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class MainMenuViewController {
	
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
