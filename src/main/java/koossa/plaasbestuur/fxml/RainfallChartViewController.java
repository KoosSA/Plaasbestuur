package koossa.plaasbestuur.fxml;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class RainfallChartViewController {
	
	@FXML
	LineChart<String, Number> chart;
	@FXML
	NumberAxis yAxis;
	@FXML
	CategoryAxis xAxis;
	@FXML
	Scene scene;
	
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	
	public void initialize() {
		chart.getData().add(series);
	}
	
	protected static void onShowingChart() {
		
		
		RainfallViewController.filteredEntries.forEach(val -> {
			System.out.println("Test");
			series.getData().add(new XYChart.Data<String, Number>(val.date.format(dateFormat), val.amount));
		});
		
		
	}
	

}
