package koossa.plaasbestuur.fxml;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import koossa.plaasbestuur.data.rain.ChartIntervals;

public class RainfallChartViewController {
	
	@FXML
	LineChart<String, Number> chart;
	@FXML
	NumberAxis yAxis;
	@FXML
	CategoryAxis xAxis;
	@FXML
	Scene scene;
	@FXML
	ChoiceBox<ChartIntervals> intervals;
	
	static XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static LocalDate s, e;
	private static Period p = Period.of(0, 0, 1);
	private static ChartIntervals iv = ChartIntervals.Daily;
	
	public void initialize() {
		chart.getData().add(series);
		chart.setLegendVisible(false);
		intervals.setItems(FXCollections.observableArrayList(ChartIntervals.values()));
		intervals.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				changeChartInterval(intervals.getValue());
			}
		});
		intervals.setValue(ChartIntervals.Daily);
	}
	
	private static void changeChartInterval(ChartIntervals newInterval) {
		iv = newInterval;
		switch (newInterval) {
		case Daily:
			dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			p = Period.of(0, 0, 1);
			break;
		case Monthly:
			dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
			p = Period.of(0, 1, 0);
			break;
		case Yearly:
			dateFormat = DateTimeFormatter.ofPattern("yyyy");
			p = Period.of(1, 0, 0);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + newInterval);
		}
		if (s != null && e != null) {
			onShowingChart(s, e);
		}
	}
	
	protected static void onShowingChart(LocalDate startDate, LocalDate endDate) {
		s = startDate;
		e = endDate;
		series.getData().clear();
		
		List<String> dates = new ArrayList<String>();
		List<Double> amounts = new ArrayList<Double>();
		
		startDate.datesUntil(endDate, p).forEach(date -> dates.add(date.format(dateFormat)));
		
		switch (iv) {
		case Daily: 
			for (int i = 0; i < dates.size(); i++) {
				double value = 0;
				for (int y = 0; y < RainfallViewController.filteredEntries.size(); y++) {
					RainfallViewController.RainEntry ent = RainfallViewController.filteredEntries.get(y);
					if (ent.date.format(dateFormat).equals(dates.get(i))) {
						value = ent.amount;
						break;
					}
				}
				amounts.add(i, value);
			}
			break;
		default:
			for (int i = 0; i < dates.size(); i++) {
				double value = 0;
				for (int y = 0; y < RainfallViewController.filteredEntries.size(); y++) {
					RainfallViewController.RainEntry ent = RainfallViewController.filteredEntries.get(y);
					if (ent.date.format(dateFormat).equals(dates.get(i))) {
						value += ent.amount;
					}
				}
				amounts.add(i, value);
			}
			break;
		}
		
		for (int i = 0; i < dates.size(); i++) {
			XYChart.Data<String, Number> entry = new XYChart.Data<String, Number>(dates.get(i), amounts.get(i));
			series.getData().add(entry);
		}
		
		
	}
	

}
