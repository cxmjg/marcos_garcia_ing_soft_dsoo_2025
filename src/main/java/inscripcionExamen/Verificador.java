package inscripcionExamen;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

public class Verificador extends InputVerifier {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	private String validar;

	public Verificador(String validar) {
		this.validar = validar;
	}

	@Override
	public boolean verify(JComponent input) {
		if (this.validar.toUpperCase() == "EMAIL") {
			validarEmail(input);
		}
		return true;
	}

	public boolean validarEmail(JComponent input) {
		JFormattedTextField textField = (JFormattedTextField) input;
		String email = textField.getText();

		if (EMAIL_PATTERN.matcher(email).matches()) {
			return true;
		} else {
			textField.setText("");
			return false;
		}

	}
}