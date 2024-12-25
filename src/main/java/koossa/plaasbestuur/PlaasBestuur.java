package koossa.plaasbestuur;

import java.util.prefs.Preferences;

import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;

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
	private static LifecycleService lifecycle;
	private static StorageService storage;
	private static DisplayService display;

	public static void main(String[] args) {
		PlaasBestuur.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		lifecycle = Services.get(LifecycleService.class).get();
		storage = Services.get(StorageService.class).get();
		display = Services.get(DisplayService.class).get();
		
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
		if (!Platform.isDesktop()) {
			stage.setWidth(display.getScreenResolution().getWidth());
			stage.setHeight(display.getScreenResolution().getHeight());
			stage.setFullScreen(true);
		}
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
