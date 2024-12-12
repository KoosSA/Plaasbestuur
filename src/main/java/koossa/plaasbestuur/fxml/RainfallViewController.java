package koossa.plaasbestuur.fxml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

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
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.data.utils.UserData;
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
	Label stats_totalFiltered;
	@FXML
	ListView<RainEntry> entry_container;
	
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private ObservableList<RainEntry> entries = FXCollections.observableArrayList();
	protected static ObservableList<RainEntry> filteredEntries = FXCollections.observableArrayList();
	private Scene chartScene = FxmlViewManager.getScene(FxmlViewNames.RAINFALL_CHART_VIEW);
	private static RainfallViewController instance;
	
	public void initialize() {
		RainfallViewController.instance = this;
		try {
			UserData.getRainfallData().load();
			filter_location.getItems().addAll(UserData.getRainfallData().getLocations());
			entry_location.getItems().addAll(UserData.getRainfallData().getLocations());
			UserData.getRainfallData().getRainEntries().values().forEach(list -> {
				list.forEach(entry  -> {
					entries.add(new RainEntry(entry.getDate(), entry.getLocation(), Double.toString(entry.getAmount())));
				});
			});
		} catch(Exception e) {}
		entry_date.setValue(LocalDate.now());
		filter_endDate.setValue(LocalDate.now());
		filter_startDate.setValue(LocalDate.of(LocalDate.now().getYear() - 1 , LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
		entry_container.setItems(filteredEntries);
		onFilter();
	}
	
	public void onFilter() {
		filteredEntries.clear();
		if (filter_endDate.getValue() != null && filter_startDate.getValue() != null && filter_location.getValue() != null) {
			entries.forEach(value -> {
				if (value.location.equalsIgnoreCase(filter_location.getValue()))
					if (koossa.plaasbestuur.utils.Filter.onFilterByDate(filter_startDate.getValue(), filter_endDate.getValue(), value.date)) {
						filteredEntries.add(value);
					}
			});
		} else {
			filteredEntries.addAll(entries);
		}
		
		double ave = 0;
		for (int i = 0; i < filteredEntries.size(); i++) {
			ave += filteredEntries.get(i).amount;
		}
		stats_totalFiltered.setText(String.valueOf(Math.round(ave * 100.0) / 100.0));
		ave = ave / (double) filteredEntries.size();
		if (ave == Double.NaN) ave = 0;
		stats_averageFiltered.setText(String.valueOf(Math.round(ave * 100.0)/100.0));
	}
	
	public void onNewEntry() {
		UserData.getRainfallData().addEntry(new koossa.plaasbestuur.data.rain.RainEntry(entry_date.getValue(), entry_location.getValue(), Double.parseDouble(entry_amount.getText())));
		entries.add(new RainEntry(entry_date.getValue(), entry_location.getValue(), entry_amount.getText()));
		Collections.sort(entries, (a,b)->a.date.compareTo(b.date));
		onFilter();
		UserData.getRainfallData().save();
	}
	
	public void onDeleteEntry() {
		RainEntry toremove = entry_container.getSelectionModel().getSelectedItem();
		List<koossa.plaasbestuur.data.rain.RainEntry> list = UserData.getRainfallData().getRainEntriesByLocation(toremove.location);
		for (int i =0; i < list.size(); i++) {
			if (list.get(i).getDate().isEqual(toremove.date) && list.get(i).getAmount() == toremove.amount) {
				list.remove(i);
			}
		}
		UserData.getRainfallData().save();
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
		RainfallChartViewController.onShowingChart(filter_startDate.getValue(), filter_endDate.getValue());
		pop.centerOnScreen();
		pop.showAndWait();
	}
	
	public void onMainMenu () {
		UserData.getRainfallData().save();
		PlaasBestuur.switchView(FxmlViewNames.MAIN_APP_VIEW);
	}
	
	public void onExportData() {
		//TODO Add rainfall data export
	}
	
	public void onEditLocations() {
		Stage pop = new Stage();
		pop.initOwner(scene.getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.setTitle(FxmlViewManager.getLanguageBundle().getString("locationsEdit"));
		pop.setScene(FxmlViewManager.getScene(FxmlViewNames.LOCATIONS_EDIT_VIEW));
		LocationsEditViewController.setLocManager(UserData.getRainfallData());
		pop.showAndWait();
		pop.centerOnScreen();
		entry_location.getItems().clear();
		entry_location.getItems().addAll(UserData.getRainfallData().getLocations());
		filter_location.getItems().clear();
		filter_location.getItems().addAll(UserData.getRainfallData().getLocations());
	}
	
	public void changeLocationName(String old, String nuw) {
		entries.forEach(e -> {
			if (e.location.equalsIgnoreCase(old)) {
				e.setLocation(nuw);
			}
		});
		filteredEntries.forEach(e -> {
			if (e.location.equalsIgnoreCase(old)) {
				e.setLocation(nuw);
			}
		});
	}
	
	public static RainfallViewController getInstance() {
		return instance;
	}
	
	protected class RainEntry extends GridPane {
		protected LocalDate date;
		protected double amount;
		protected String location = "@ unknown";
		private Label l;
		public RainEntry(LocalDate date, String location, String amount) {
			if (date == null) date = LocalDate.now();
			this.date = date;
			if (location != null) this.location = location;
			if (amount == null || amount.length() <= 0) amount = "0";
			this.amount = Double.parseDouble(amount);
			setHgap(15);
			setPadding(new Insets(0, 0, 10, 0));
			setAlignment(Pos.TOP_CENTER);
			Label d = new Label(date.format(dateFormat));
			l = new Label(location);
			Label a = new Label(amount);
			d.setFont(new Font(14));
			a.setFont(new Font(14));
			l.setFont(new Font(14));
			add(d, 0, 0);
			add(l, 1, 0);
			add(a, 2, 0);
		}
		
		public void setLocation(String location) {
			this.location = location;
			l.setText(location);
		}
	}

}
