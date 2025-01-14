package koossa.plaasbestuur.data.livestock;

import java.net.URL;

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
		return name;
	}
	
	public URL getImageURI() {
		return getClass().getResource("..\\..\\images\\livestock\\" + image);
	}

}
