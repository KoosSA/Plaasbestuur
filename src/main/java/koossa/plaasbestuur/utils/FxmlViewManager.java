package koossa.plaasbestuur.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import koossa.plaasbestuur.PlaasBestuur;

public class FxmlViewManager {
	
	private static Map<FxmlViewNames, Scene> views = new HashMap<FxmlViewNames, Scene>();
	private static Language selectedLang = Language.ENGLISH_UK;
	
	private static <T> Scene loadView(String name) throws IOException {
		Scene scn = null;
		
		ResourceBundle bundle = ResourceBundle.getBundle("koossa/plaasbestuur/lang/" + selectedLang.getData());
		
		T obj = FXMLLoader.load(PlaasBestuur.class.getResource("fxml/" + name), bundle);
		if (obj.getClass().equals(Scene.class)) {
			return Scene.class.cast(obj);
		} else {
			scn = new Scene((Parent) obj);
			return scn;
		}
	}
	
	public static Scene getScene(FxmlViewNames viewName) {
		Scene v = views.get(viewName);
		if (v == null) {
			try {
				v = loadView(viewName.getData());
				views.put(viewName, v);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return v;
	}
	
	public static Language getCurrentLanguage() {
		return selectedLang;
	}

	public static void switchLanguage(Language language, FxmlViewNames currentView) {
		views.clear();
		selectedLang = language;
		
		PlaasBestuur.switchView(currentView);
	}

}
