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
	private static String currentUser;

	public static void main(String[] args) {
		PlaasBestuur.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		PlaasBestuur.stage = stage;
		
		switchView(FxmlViewNames.LOGIN_VIEW);
		
		stage.setTitle(FxmlViewManager.getLanguageBundle().getString("appTitle"));
		stage.show();
		stage.centerOnScreen();
		stage.requestFocus();
		stage.toFront();
	}
	
	public static void switchView(FxmlViewNames viewName) {
		currentView = viewName;
		stage.setScene(FxmlViewManager.getScene(viewName));
		stage.centerOnScreen();
	}
	
	public static void setLanguage(Language language) {
		if (FxmlViewManager.getCurrentLanguage() == language) {
			return;
		}
		FxmlViewManager.switchLanguage(language, currentView);
		stage.setTitle(FxmlViewManager.getLanguageBundle().getString("appTitle"));
	}
	
	public static FxmlViewNames getCurrentView() {
		return currentView;
	}
	
	public static Preferences getPreferences() {
		return preferences;
	}

	public static String getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(String currentUser) {
		PlaasBestuur.currentUser = currentUser;
	}
}
