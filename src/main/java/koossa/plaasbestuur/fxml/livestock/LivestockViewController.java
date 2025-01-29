package koossa.plaasbestuur.fxml.livestock;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
	@FXML
	AnchorPane pane;
	
	private Scene typeViewScene;
	
	public void initialize() {
		pane.setBackground(FxmlViewManager.getBackground());
		UserData.getLivestockData().load();
		typeViewScene = FxmlViewManager.getScene(FxmlViewNames.LIVESTOCK_TYPE_VIEW);
		for (int i = 0; i < LivestockTypes.values().length; i++) {
			LivestockTypes type = LivestockTypes.values()[i];
//			System.out.println("Animaltype: " + type + "Name: " + type.getName() + "Img: " + type.getImageURI());
			Button btn = new Button(type.getName());
//			System.out.println("Btn: " + btn);
			btn.setPrefWidth(75);
			btn.setPrefHeight(75);
			btn.setTextAlignment(TextAlignment.CENTER);
			flowPane.getChildren().add(btn);
			ImageView img = new ImageView(type.getImageURI().toString());
			img.setFitWidth(30);
			img.setFitHeight(30);
			btn.setGraphic(img);
			btn.setContentDisplay(ContentDisplay.TOP);
			btn.setOnAction(event -> {
				LivestockTypeViewController.setAnimalType(type);
				PlaasBestuur.switchView(typeViewScene, FxmlViewNames.LIVESTOCK_TYPE_VIEW);
			});
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
		LivestockTypeViewController.updateLocations();
		UserData.getLivestockData().save();
	}
	
	public void onMainMenu() {
		PlaasBestuur.switchView(FxmlViewNames.MAIN_APP_VIEW);
	}

}
