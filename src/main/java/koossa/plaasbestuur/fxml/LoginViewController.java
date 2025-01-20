package koossa.plaasbestuur.fxml;

import java.util.prefs.BackingStoreException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewManager;
import koossa.plaasbestuur.utils.FxmlViewNames;
import koossa.plaasbestuur.utils.Screen;

//TODO Change language from here and store language preference
public class LoginViewController {
	
	@FXML
	TextField username_field;
	@FXML
	PasswordField password_field;
	@FXML
	CheckBox rememberCredentials_check;
	@FXML
	AnchorPane pane;
	
	public void initialize() {
		pane.setBackground(FxmlViewManager.getBackground());
		if (PlaasBestuur.getPreferences().getBoolean("rememberCredentials", false)) {
			username_field.setText(PlaasBestuur.getPreferences().get("db_username", null));
			password_field.setText(PlaasBestuur.getPreferences().get("db_password", null));
			rememberCredentials_check.setSelected(PlaasBestuur.getPreferences().getBoolean("rememberCredentials", false));
		}
	}
	
	public void onRegister() {
		PlaasBestuur.switchView(FxmlViewNames.REGISTER_VIEW);
	}
	
	public void onCancel() {
		PlaasBestuur.exit();
	}
	
	public void onLogin() {
		if (PlaasBestuur.getPreferences().get("db_username", null) == null) {
			Alert err = new Alert(AlertType.INFORMATION);
			//LOOKAT Add translations
			err.setTitle("Login failed");
			err.setHeaderText("Please register the user first.");
			err.setContentText("Register the app using the button below before logging in.");
			Screen.fitToWidthIfMobile(username_field.getScene().getWindow());
			err.showAndWait().ifPresent(action -> {
				if (action == ButtonType.OK) {
					onRegister();
				}
			});
		} else if (username_field.getText().length() > 0 && password_field.getText().length() > 0) {
			if (username_field.getText().equals(PlaasBestuur.getPreferences().get("db_username", null)) && password_field.getText().equals(PlaasBestuur.getPreferences().get("db_password", null))) {
				PlaasBestuur.getPreferences().putBoolean("rememberCredentials", rememberCredentials_check.isSelected());
				try {
					PlaasBestuur.getPreferences().flush();
				} catch (BackingStoreException e) {
					e.printStackTrace();
				}
				PlaasBestuur.setCurrentUser(username_field.getText());
				PlaasBestuur.switchView(FxmlViewNames.MAIN_APP_VIEW);
			}
		}
	}

}
