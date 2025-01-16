package koossa.plaasbestuur.fxml.livestock.util;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import koossa.plaasbestuur.data.livestock.LivestockEntry;

public class ListEntryElement extends VBox {
	
	private LivestockEntry entry;
	private Label name_lbl, age_lbl, race_lbl;
	
	public ListEntryElement(LivestockEntry entry) {
		this.entry = entry;
		this.name_lbl = new Label(entry.getBrand());
		this.age_lbl = new Label(Integer.toString(entry.getAgeYears()));
		this.race_lbl = new Label(entry.getRace());
		
		this.getChildren().addAll(name_lbl, race_lbl, age_lbl);
		this.setSpacing(10);
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
