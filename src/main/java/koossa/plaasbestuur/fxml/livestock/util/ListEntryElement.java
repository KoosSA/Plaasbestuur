package koossa.plaasbestuur.fxml.livestock.util;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import koossa.plaasbestuur.data.livestock.LivestockEntry;

public class ListEntryElement extends HBox {
	
	private LivestockEntry entry;
	private Label name_lbl, age_lbl, race_lbl, location_lbl;
	
	public ListEntryElement(LivestockEntry entry) {
		this.entry = entry;
		this.name_lbl = new Label(entry.getBrand());
		this.age_lbl = new Label("Age: " + Integer.toString(entry.getAgeYears()));
		this.race_lbl = new Label(entry.getRace() + " - " + entry.getGender());
		this.location_lbl = new Label("@ " + entry.getLocation());
		
		this.getChildren().addAll(name_lbl, race_lbl, age_lbl, location_lbl);
		this.setSpacing(10);
		this.setAlignment(Pos.TOP_CENTER);
	}
	
	public LivestockEntry getEntry() {
		return entry;
	}
	
	public void updateListEntry() {
		this.name_lbl.setText(entry.getBrand());
		this.age_lbl.setText(Integer.toString(entry.getAgeYears()));
		this.race_lbl.setText(entry.getRace());
	}

}
