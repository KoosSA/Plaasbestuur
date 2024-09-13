package koossa.plaasbestuur.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import koossa.plaasbestuur.PlaasBestuur;
import koossa.plaasbestuur.utils.FxmlViewNames;

public class RegisterViewController {
	
	@FXML
	TextField username_field;
	@FXML
	PasswordField password_field1;
	@FXML
	PasswordField password_field2;
	@FXML
	Label passwordHelper;
	@FXML
	Button register_btn;
	
	public void onCancel() {
		
	}
	
	public void onPasswordChangeListener() {
		if (password_field1.getText().equals(password_field2.getText())) {
			if (password_field1.getText().length() > 0) {
				register_btn.setDisable(false);
			}
			passwordHelper.setVisible(false);
		} else {
			register_btn.setDisable(true);
			passwordHelper.setVisible(true);
		}
	}
	
	public void onRegister() {
		PlaasBestuur.getPreferences().put("db_username", username_field.getText());
	    PlaasBestuur.getPreferences().put("db_password", password_field1.getText());
	    
	    PlaasBestuur.switchView(FxmlViewNames.LOGIN_VIEW);
	}

}
