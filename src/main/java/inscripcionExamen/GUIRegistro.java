package inscripcionExamen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class GUIRegistro {
	
	private JPanel frameRegistro;
	private JTextField valorNuevoUsuarioNombre;
	private JTextField valorNuevoUsuarioApellido;
	private JPasswordField valorNuevoUsuarioPassword;
	private JFormattedTextField valorNuevoUsuarioEmail;
	private JLabel labelRequeridoPasswordNuevoUsuario;
	private JLabel labelRequeridoApellidoNuevoUsuario;
	private JLabel labelRequeridoNombreNuevoUsuario;
	private JLabel labelRequeridoEmailNuevoUsuario;
	private JLabel labelMensajeRegistro;
	private JButton btnGuardarNuevoUsuario;
	private GUILogin login;
	Boolean emailOK;
	
	public GUIRegistro() {
		this.crearGUI();
	}
	
	
	private void crearGUI() {
		frameRegistro = new JPanel();
		frameRegistro.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { RowSpec.decode("fill:default:grow"), FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, RowSpec.decode("fill:default:grow"), }));
		labelMensajeRegistro = new JLabel("");
		frameRegistro.add(labelMensajeRegistro, "4, 2, 5, 1, center, default");
		JLabel labelNuevoUsuarioNombre = new JLabel("Nombre");
		frameRegistro.add(labelNuevoUsuarioNombre, "4, 4, right, default");
		valorNuevoUsuarioNombre = new JTextField();
		frameRegistro.add(valorNuevoUsuarioNombre, "6, 4, 2, 1, fill, default");
		valorNuevoUsuarioNombre.setColumns(10);
		labelRequeridoNombreNuevoUsuario = new JLabel("");
		labelRequeridoNombreNuevoUsuario.setForeground(new Color(255, 0, 0));
		frameRegistro.add(labelRequeridoNombreNuevoUsuario, "8, 4");
		JLabel labelNuevoUsuarioApellido = new JLabel("Apellido");
		frameRegistro.add(labelNuevoUsuarioApellido, "4, 6, right, default");
		valorNuevoUsuarioApellido = new JTextField();
		frameRegistro.add(valorNuevoUsuarioApellido, "6, 6, 2, 1, fill, default");
		valorNuevoUsuarioApellido.setColumns(10);
		labelRequeridoApellidoNuevoUsuario = new JLabel("");
		labelRequeridoApellidoNuevoUsuario.setForeground(new Color(255, 0, 0));
		frameRegistro.add(labelRequeridoApellidoNuevoUsuario, "8, 6");
		JLabel labelNuevoUsuarioEmail = new JLabel("Email");
		frameRegistro.add(labelNuevoUsuarioEmail, "4, 8, right, default");
		valorNuevoUsuarioEmail = new JFormattedTextField();
		valorNuevoUsuarioEmail.setInputVerifier(new Verificador("EMAIL"));
		frameRegistro.add(valorNuevoUsuarioEmail, "6, 8, 2, 1, fill, default");
		labelRequeridoEmailNuevoUsuario = new JLabel("");
		labelRequeridoEmailNuevoUsuario.setForeground(new Color(255, 0, 0));
		frameRegistro.add(labelRequeridoEmailNuevoUsuario, "8, 8");
		JLabel labelNuevoUsuarioPassword = new JLabel("Password");
		frameRegistro.add(labelNuevoUsuarioPassword, "4, 10, right, default");
		valorNuevoUsuarioPassword = new JPasswordField();
		frameRegistro.add(valorNuevoUsuarioPassword, "6, 10, 2, 1, fill, default");
		JButton btnCancelarNuevoUsuario = new JButton("Cancelar");
		btnCancelarNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarRegistro();
			}
		});
		labelRequeridoPasswordNuevoUsuario = new JLabel("");
		labelRequeridoPasswordNuevoUsuario.setForeground(new Color(255, 0, 0));
		frameRegistro.add(labelRequeridoPasswordNuevoUsuario, "8, 10");
		frameRegistro.add(btnCancelarNuevoUsuario, "6, 12, left, default");
		btnGuardarNuevoUsuario = new JButton("Guardar");
		frameRegistro.add(btnGuardarNuevoUsuario, "7, 12, right, default");
	}
	
	public void setLogin(GUILogin login) {
		this.login = login;
	}
	
	public JPanel getPanel() {
		return frameRegistro;
	}
	
	public void limpiaRegistro() {
		this.valorNuevoUsuarioNombre.setText(null);
		this.valorNuevoUsuarioApellido.setText(null);
		this.valorNuevoUsuarioEmail.setText(null);
		this.valorNuevoUsuarioPassword.setText(null);
	}
	
	public void asignarAccionBtnGuardarNuevoUsuario(ActionListener accion) {
		this.btnGuardarNuevoUsuario.addActionListener(accion);
	}

	public void setMensajeRegistro(String mensaje) {
		this.labelMensajeRegistro.setText(mensaje);
	}

	public void setNombreNuevoUsuarioRequerido() {
		this.labelRequeridoNombreNuevoUsuario.setText("*");
	}

	public void setApellidoNuevoUsuarioRequerido() {
		this.labelRequeridoApellidoNuevoUsuario.setText("*");
	}

	public void setEmailNuevoUsuarioRequerido() {
		this.labelRequeridoEmailNuevoUsuario.setText("*");
	}

	public void setPasswordNuevoUsuarioRequerido() {
		this.labelRequeridoPasswordNuevoUsuario.setText("*");
	}

	public void limpiarCamposRequeridosNuevoUsuario() {
		this.setMensajeRegistro("");
		this.labelRequeridoNombreNuevoUsuario.setText("");
		this.labelRequeridoApellidoNuevoUsuario.setText("");
		this.labelRequeridoEmailNuevoUsuario.setText("");
		this.labelRequeridoPasswordNuevoUsuario.setText("");
	}

	public Boolean verificarCamposRequeridosNuevousuario() {
		this.limpiarCamposRequeridosNuevoUsuario();
		String nombre = this.getNombreNuevoUsuario();
		String apellido = this.getApellidoNuevoUsuario();
		String email = this.getEmailNuevoUsuario();
		String password = this.getPasswordNuevoUsuario();
		String mensaje = "";
		Boolean camposCompletos = true;
		Verificador campoEmail = new Verificador("EMAIL");
		if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || password.isBlank()
				|| !campoEmail.verify(this.valorNuevoUsuarioEmail)) {
			mensaje = "Todos los campos son requeridos";
			camposCompletos = false;
			if (nombre.isBlank()) {
				this.setNombreNuevoUsuarioRequerido();
			}
			if (apellido.isBlank()) {
				this.setApellidoNuevoUsuarioRequerido();
			}
			if (email.isBlank() || !campoEmail.verify(this.valorNuevoUsuarioEmail)) {
				this.setEmailNuevoUsuarioRequerido();
			}
			if (password.isBlank()) {
				this.setPasswordNuevoUsuarioRequerido();
			}
		}
		this.setMensajeRegistro(mensaje);
		return camposCompletos;
	}

	public String getNombreNuevoUsuario() {
		return this.valorNuevoUsuarioNombre.getText();
	}

	public String getApellidoNuevoUsuario() {
		return this.valorNuevoUsuarioApellido.getText();
	}

	public String getPasswordNuevoUsuario() {
		return this.valorNuevoUsuarioPassword.getText();
	}

	public String getEmailNuevoUsuario() {
		return this.valorNuevoUsuarioEmail.getText();
	}

	public void ocultarRegistro() {
		login.ocultarRegistro();
	}

	public void cancelarRegistro() {
		this.limpiarCamposRequeridosNuevoUsuario();
		this.valorNuevoUsuarioNombre.setText(null);
		this.valorNuevoUsuarioApellido.setText(null);
		this.valorNuevoUsuarioEmail.setText(null);
		this.valorNuevoUsuarioPassword.setText(null);
		ocultarRegistro();
	}

}
