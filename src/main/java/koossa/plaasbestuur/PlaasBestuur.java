package koossa.plaasbestuur;

import java.util.prefs.Preferences;

import com.gluonhq.attach.connectivity.ConnectivityService;
import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.attach.util.Services;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Language;
import koossa.plaasbestuur.utils.Screen;

public class PlaasBestuur extends Application {
	
	private static Stage stage;
	private static FxmlViewNames currentView;
	private static Preferences preferences = Preferences.userNodeForPackage(PlaasBestuur.class);
	private static String currentUser;
	private static LifecycleService lifecycle;
	private static StorageService storage;
	private static ConnectivityService connectivity;

	public static void main(String[] args) {
		PlaasBestuur.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		lifecycle = Services.get(LifecycleService.class).get();
		storage = Services.get(StorageService.class).get();
		Screen.init();
		
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
		Scene sc = FxmlViewManager.getScene(viewName);
		stage.setScene(sc);
//		if (!Platform.isDesktop()) {
//			stage.setWidth(display.getDefaultDimensions().getWidth());
//			stage.setHeight(display.getDefaultDimensions().getHeight());
//			stage.setMaxWidth(display.getDefaultDimensions().getWidth());
//			stage.setMaximized(true);
//		}
		Screen.fitToScreenIfMobile(stage);
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
	
	public static void exit() {
		lifecycle.shutdown();
	}
	
	public static StorageService getStorage() {
		return storage;
	}
	
}
