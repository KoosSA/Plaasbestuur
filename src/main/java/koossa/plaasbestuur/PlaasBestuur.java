package koossa.plaasbestuur;

import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.stage.Stage;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Language;

public class PlaasBestuur extends Application {
	
	private static Stage stage;
	private static FxmlViewNames currentView;
	private static Preferences preferences = Preferences.userNodeForPackage(PlaasBestuur.class);

	@Override
	public void start(Stage stage) throws Exception {
		PlaasBestuur.stage = stage;
		
		switchView(FxmlViewNames.DEBUG_VIEW);
		
		stage.show();
	}
	
	public static void switchView(FxmlViewNames viewName) {
		currentView = viewName;
		stage.setScene(FxmlViewManager.getScene(viewName));
	}
	
	public static void setLanguage(Language language) {
		if (FxmlViewManager.getCurrentLanguage() == language) {
			return;
		}
		FxmlViewManager.switchLanguage(language, currentView);
	}
	
	public static FxmlViewNames getCurrentView() {
		return currentView;
	}
	
	public static Preferences getPreferences() {
		return preferences;
	}

	
}
