package koossa.plaasbestuur.fxml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class RainfallViewController {
	
	@FXML
	DatePicker filter_startDate;
	@FXML
	DatePicker filter_endDate;
	@FXML
	ChoiceBox<String> filter_location;
	@FXML
	DatePicker entry_date;
	@FXML
	ChoiceBox<String> entry_location;
	@FXML
	TextField entry_amount;
	@FXML
	Label stats_averageFiltered;
	@FXML
	ListView<RainEntry> entry_container;
	
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	ObservableList<RainEntry> entries = FXCollections.observableArrayList();
	
	public void initialize() {
		entry_date.setValue(LocalDate.now());
		filter_endDate.setValue(LocalDate.now());
		entry_container.setItems(entries);
	}
	
	public void onFilter() {
		
	}
	
	public void onNewEntry() {
		//entry_container.getChildren().add(new RainEntry(entry_date.getValue(), entry_location.getValue(), entry_amount.getText()));
		entries.add(new RainEntry(entry_date.getValue(), entry_location.getValue(), entry_amount.getText()));
	}
	
	public void onDeleteEntry() {
		int index = entry_container.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			entries.remove(index);
			entry_container.getSelectionModel().clearSelection();
		}
	}
	
	
	private class RainEntry extends GridPane {
		public RainEntry(LocalDate date, String location, String amount) {
			if (date == null) date = LocalDate.now();
			if (location == null) location = "@ unknown";
			if (amount == null || amount.length() <= 0) amount = "0";
			setHgap(15);
			setPadding(new Insets(0, 0, 10, 0));
			setAlignment(Pos.TOP_CENTER);
			Label d = new Label(date.format(dateFormat));
			Label l = new Label(location);
			Label a = new Label(amount);
			d.setFont(new Font(14));
			a.setFont(new Font(14));
			l.setFont(new Font(14));
			add(d, 0, 0);
			add(l, 1, 0);
			add(a, 2, 0);
		}
	}

}
