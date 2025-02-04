package koossa.plaasbestuur.data.livestock;

import java.net.URL;

import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewManager;

public enum LivestockTypes {
	
	SHEEP("sheep", "ram.png"),
	PIGS("pigs", "pig2.png"),
	CATTLE("cattle", "cow2.png"),
	GOATS("goats", "goat.png"),
	DONKEYS("donkeys", "horse.png"),
	BUFFALOS("buffalos", "water_buffalo.png"),
	HORSES("horses", "racehorse.png");
	
	
	private String image;
	private String name;
	private AnimalUtilData utilData;
	
	LivestockTypes(String name, String imageName) {
		this.name = name;
		this.image = imageName;
		this.utilData = new AnimalUtilData();
	}
	
	public String getName() {
		return FxmlViewManager.getLanguageBundle().getString(name);
	}
	
	public URL getImageURI() {
		return PlaasBestuur.class.getResource("/koossa/plaasbestuur/images/livestock/" + image);
	}
	
	public AnimalUtilData getUtilData() {
		return utilData;
	}

}
