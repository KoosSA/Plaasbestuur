package koossa.plaasbestuur.fxml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.gluonhq.attach.util.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
	ListView<RainEntry> entry_container;
	
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private ObservableList<RainEntry> entries = FXCollections.observableArrayList();
	protected static ObservableList<RainEntry> filteredEntries = FXCollections.observableArrayList();
	private Scene filterScene;
	private Scene statsScene;
	private Scene newEntryScene;
	private Scene chartScene;
	private static RainfallViewController instance;
	private LocalDate filterStartDate = LocalDate.of(LocalDate.now().getYear() - 1 , LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
	private LocalDate filterEndDate = LocalDate.now();
	private String filterLocation;
	
	public void initialize() {
		UserData.getRainfallData().load();
		RainfallViewController.instance = this;
		try {
			
			UserData.getRainfallData().getRainEntries().values().forEach(list -> {
				list.forEach(entry  -> {
					entries.add(new RainEntry(entry.getDate(), entry.getLocation(), Double.toString(entry.getAmount())));
				});
			});
			filterLocation = entries.getFirst().location;
		} catch(Exception e) {}
		filterScene = FxmlViewManager.getScene(FxmlViewNames.RAINFALL_FILTER_VIEW);
		statsScene = FxmlViewManager.getScene(FxmlViewNames.RAINFALL_STATS_VIEW);
		newEntryScene = FxmlViewManager.getScene(FxmlViewNames.RAINFALL_NEW_ENTRY_VIEW);
		chartScene = FxmlViewManager.getScene(FxmlViewNames.RAINFALL_CHART_VIEW);
		entry_container.setItems(filteredEntries);
		onFilter();
	}
	
	public void showFilter() {
		Stage pop = new Stage();
		pop.setScene(filterScene);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.initOwner(scene.getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.centerOnScreen();
		pop.requestFocus();
		pop.toFront();
		RainfallFilterViewController.getInstance().setStage(pop);
		pop.showAndWait();
	}
	
	protected void onFilter() {
		filteredEntries.clear();
		if (filterEndDate != null && filterStartDate != null && filterLocation != null) {
			entries.forEach(value -> {
				if (value.location.equalsIgnoreCase(filterLocation))
					if (koossa.plaasbestuur.utils.Filter.onFilterByDate(filterStartDate, filterEndDate, value.date)) {
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
		double tot = ave;
		ave = ave / (double) filteredEntries.size();
		if (ave == Double.NaN) {
			ave = 0;
		}
		RainfallStatsViewController.updateValues(ave, tot);
	}
	
	public void onStats() {
		Stage pop = new Stage();
		pop.setScene(statsScene);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.initOwner(scene.getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.centerOnScreen();
		pop.requestFocus();
		pop.toFront();
		if (!Platform.isDesktop()) {
			pop.setWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
			pop.setHeight(PlaasBestuur.getDisplay().getDefaultDimensions().getHeight());
			pop.setMaxWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
		}
		pop.showAndWait();
	}
	
	public void onNewEntry() {
		Stage pop = new Stage();
		pop.setScene(newEntryScene);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.initOwner(scene.getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.centerOnScreen();
		pop.requestFocus();
		pop.toFront();
		RainfallNewEntryViewController.getInstance().setStage(pop);
		if (!Platform.isDesktop()) {
			pop.setWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
			pop.setHeight(PlaasBestuur.getDisplay().getDefaultDimensions().getHeight());
			pop.setMaxWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
		}
		pop.showAndWait();
	}
	
	protected void addEntry(LocalDate date, String location, String amount) {
		entries.add(new RainEntry(date, location, amount));
		Collections.sort(entries, (a,b)->a.date.compareTo(b.date));
		onFilter();
		UserData.getRainfallData().save();
	}
	
	public void onDeleteEntry() {
		RainEntry toremove = entry_container.getSelectionModel().getSelectedItem();
		if (toremove != null) {
			Alert deletionAlert = new Alert(AlertType.CONFIRMATION);
			deletionAlert.setHeaderText(FxmlViewManager.getLanguageBundle().getString("confirmDelete") + toremove.date.toString() + " " + toremove.location + " " + toremove.amount);
			deletionAlert.setContentText(FxmlViewManager.getLanguageBundle().getString("rainEntryDeleteConfirm"));
			if (!Platform.isDesktop()) {
				deletionAlert.setWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
			}
			if (deletionAlert.showAndWait().get().getButtonData() == ButtonData.OK_DONE) {
				List<koossa.plaasbestuur.data.rain.RainEntry> list = UserData.getRainfallData()
						.getRainEntriesByLocation(toremove.location);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getDate().isEqual(toremove.date) && list.get(i).getAmount() == toremove.amount) {
						list.remove(i);
					}
				}
				int index = entry_container.getSelectionModel().getSelectedIndex();
				if (index != -1) {
					entries.remove(index);
					entry_container.getSelectionModel().clearSelection();
				}
				onFilter();
				UserData.getRainfallData().save();
			}
		}
	}
	
	public void onShowGraph() {
		Stage pop = new Stage();
		pop.initOwner(scene.getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.setScene(chartScene);
		RainfallChartViewController.onShowingChart(filterStartDate, filterEndDate);
		pop.centerOnScreen();
		pop.requestFocus();
		pop.toFront();
		if (!Platform.isDesktop()) {
			pop.setWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
			pop.setHeight(PlaasBestuur.getDisplay().getDefaultDimensions().getHeight());
			pop.setMaxWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
		}
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
		LocationsEditViewController.setStage(pop);
		if (!Platform.isDesktop()) {
			pop.setWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
			pop.setHeight(PlaasBestuur.getDisplay().getDefaultDimensions().getHeight());
			pop.setMaxWidth(PlaasBestuur.getDisplay().getDefaultDimensions().getWidth());
		}
		pop.centerOnScreen();
		pop.showAndWait();
		RainfallNewEntryViewController.getInstance().onEditLocations();
		RainfallFilterViewController.getInstance().onEditLocations();
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

	public void setFilterData(LocalDate startDate, LocalDate endDate, String location) {
		this.filterEndDate = endDate;
		this.filterStartDate = startDate;
		this.filterLocation = location;
	}

}
