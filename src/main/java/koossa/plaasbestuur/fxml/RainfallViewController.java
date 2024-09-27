package koossa.plaasbestuur.fxml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class RainfallViewController {
	
	@FXML
	Scene scene;
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
	
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private ObservableList<RainEntry> entries = FXCollections.observableArrayList();
	protected static ObservableList<RainEntry> filteredEntries = FXCollections.observableArrayList();
	private Scene chartScene = FxmlViewManager.getScene(FxmlViewNames.RAINFALL_CHART_VIEW);
	
	public void initialize() {
		entry_date.setValue(LocalDate.now());
		//filter_endDate.setValue(LocalDate.now());
		entry_container.setItems(filteredEntries);
	}
	
	public void onFilter() {
		filteredEntries.clear();
		if (filter_endDate.getValue() != null && filter_startDate.getValue() != null) {
			System.out.println("Valid dates");
			entries.forEach(value -> {
				if (koossa.plaasbestuur.utils.Filter.onFilterByDate(filter_startDate.getValue(), filter_endDate.getValue(), value.date))
					filteredEntries.add(value);
			});
		} else {
			filteredEntries.addAll(entries);
		}
		
		double ave = 0;
		for (int i = 0; i < filteredEntries.size(); i++) {
			ave += filteredEntries.get(i).amount;
		}
		ave = ave / (double) filteredEntries.size();
		if (ave == Double.NaN) ave = 0;
		stats_averageFiltered.setText(String.valueOf(Math.round(ave*100.0)/100.0));
	}
	
	public void onNewEntry() {
		//entry_container.getChildren().add(new RainEntry(entry_date.getValue(), entry_location.getValue(), entry_amount.getText()));
		entries.add(new RainEntry(entry_date.getValue(), entry_location.getValue(), entry_amount.getText()));
		entries.sort(rainEntrySorter);
		onFilter();
	}
	
	public void onDeleteEntry() {
		int index = entry_container.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			entries.remove(index);
			entry_container.getSelectionModel().clearSelection();
		}
		onFilter();
	}
	
	public void onShowGraph() {
		Stage pop = new Stage();
		pop.initOwner(scene.getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.setScene(chartScene);
		RainfallChartViewController.onShowingChart();
		pop.centerOnScreen();
		pop.showAndWait();
	}
	
	public void onMainMenu () {
		
	}
	
	public void onExportData() {
		
	}
	
	protected class RainEntry extends GridPane {
		protected LocalDate date;
		protected double amount;
		public RainEntry(LocalDate date, String location, String amount) {
			if (date == null) date = LocalDate.now();
			this.date = date;
			if (location == null) location = "@ unknown";
			if (amount == null || amount.length() <= 0) amount = "0";
			this.amount = Double.parseDouble(amount);
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
	
	private Comparator<RainEntry> rainEntrySorter = new Comparator<RainfallViewController.RainEntry>() {
		@Override
		public int compare(RainEntry o1, RainEntry o2) {
			if ((o1.date.getYear() - o2.date.getYear()) >= 0) {
				if ((o1.date.getDayOfYear() - o2.date.getDayOfYear()) >= 0) {
					return 1;
				} else {
					return -1;
				}
			} 
			return -1;
		}
	};

}
