package koossa.plaasbestuur.fxml;

import java.util.prefs.BackingStoreException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class LoginViewController {
	
	@FXML
	TextField username_field;
	@FXML
	PasswordField password_field;
	@FXML
	CheckBox rememberCredentials_check;
	
	public void initialize() {
		if (PlaasBestuur.getPreferences().getBoolean("rememberCredentials", false)) {
			username_field.setText(PlaasBestuur.getPreferences().get("db_username", null));
			password_field.setText(PlaasBestuur.getPreferences().get("db_password", null));
			rememberCredentials_check.setSelected(PlaasBestuur.getPreferences().getBoolean("rememberCredentials", false));
		}
	}
	
	public void onCancel() {
		
	}
	
	public void onLogin() {
		if (username_field.getText().length() > 0 && password_field.getText().length() > 0) {
			if (username_field.getText().equals(PlaasBestuur.getPreferences().get("db_username", null)) && password_field.getText().equals(PlaasBestuur.getPreferences().get("db_password", null))) {
				PlaasBestuur.getPreferences().putBoolean("rememberCredentials", rememberCredentials_check.isSelected());
				try {
					PlaasBestuur.getPreferences().flush();
				} catch (BackingStoreException e) {
					e.printStackTrace();
				}
				PlaasBestuur.switchView(FxmlViewNames.MAIN_APP_VIEW);
			}
		}
	}

}
