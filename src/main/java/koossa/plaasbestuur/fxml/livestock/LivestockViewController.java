package koossa.plaasbestuur.fxml.livestock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.data.livestock.LivestockTypes;
import koossa.plaasbestuur.data.utils.UserData;
import koossa.plaasbestuur.fxml.LocationsEditViewController;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Screen;

public class LivestockViewController {
	
	@FXML
	FlowPane flowPane;
	
	public void initialize() {
		for (int i = 0; i < LivestockTypes.values().length; i++) {
			LivestockTypes type = LivestockTypes.values()[i];
			Button btn = new Button(FxmlViewManager.getLanguageBundle().getString(type.getName()));
			btn.setPrefWidth(75);
			btn.setPrefHeight(75);
			btn.setTextAlignment(TextAlignment.CENTER);
			flowPane.getChildren().add(btn);
			ImageView img = new ImageView(type.getImageURI().toString());
			img.setFitWidth(30);
			img.setFitHeight(30);
			btn.setGraphic(img);
			btn.setContentDisplay(ContentDisplay.TOP);
		}
	}
	
	public void onEditLocations() {
		Stage pop = new Stage();
		pop.initOwner(flowPane.getScene().getWindow());
		pop.initStyle(StageStyle.UTILITY);
		pop.initModality(Modality.APPLICATION_MODAL);
		pop.setTitle(FxmlViewManager.getLanguageBundle().getString("locationsEdit"));
		pop.setScene(FxmlViewManager.getScene(FxmlViewNames.LOCATIONS_EDIT_VIEW));
		LocationsEditViewController.setLocManager(UserData.getLivestockData());
		LocationsEditViewController.setStage(pop);
		Screen.fitToScreenIfMobile(pop);
		pop.centerOnScreen();
		pop.showAndWait();
	}
	
	public void onMainMenu() {
		PlaasBestuur.switchView(FxmlViewNames.MAIN_APP_VIEW);
	}

}
