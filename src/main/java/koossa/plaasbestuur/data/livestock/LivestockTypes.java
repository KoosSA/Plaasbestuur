package koossa.plaasbestuur.data.livestock;

import java.net.URL;

import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewManager;

public enum LivestockTypes {
	
	SHEEP("sheep", "ram.png"),
	PIGS("pigs", "pig2.png"),
	CATTLE("cattle", "cow2.png"),
	GOATS("goats", "goat.png"),
	DONKEYS("donkeys", "zebra_face.png"),
	BUFFALOS("buffalos", "water_buffalo.png"),
	HORSES("horses", "racehorse.png");
	
	
	private String image;
	private String name;
	
	LivestockTypes(String name, String imageName) {
		this.name = name;
		this.image = imageName;
	}
	
	public String getName() {
		return FxmlViewManager.getLanguageBundle().getString(name);
	}
	
	public URL getImageURI() {
		return PlaasBestuur.class.getResource("/koossa/plaasbestuur/images/livestock/" + image);
	}

}
