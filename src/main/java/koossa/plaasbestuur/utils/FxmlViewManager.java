package koossa.plaasbestuur.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import koossa.plaasbestuur.PlaasBestuur;

public class FxmlViewManager {
	
	private static Map<FxmlViewNames, Scene> views = new HashMap<FxmlViewNames, Scene>();
	private static Language selectedLang = Language.ENGLISH_UK;
	private static ResourceBundle bundle = ResourceBundle.getBundle("koossa/plaasbestuur/lang/" + selectedLang.getData());
	private static Background background = new Background(new BackgroundImage(new Image(PlaasBestuur.class.getResource("/koossa/plaasbestuur/images/agtergrond.jpg").toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, true, true, false, false)));
	
	private static <T> Scene loadView(String name) throws IOException {
		Scene scn = null;
		
		bundle = ResourceBundle.getBundle("koossa/plaasbestuur/lang/" + selectedLang.getData());
		
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
	
	public static ResourceBundle getLanguageBundle() {
		return bundle;
	}
	
	public static Background getBackground() {
		return background;
	}

}
