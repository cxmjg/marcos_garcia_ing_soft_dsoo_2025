package inscripcionExamen;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GUILogin {
	private JPanel panelLogin;
	private JLabel labelLoginIncorrecto;
	private JTextField valorUsuario;
	private JPasswordField valorPassword;
	private JButton btnLogin;
	private JButton btnRegistrarse;
	private JButton btnRecuperarPassword;
	private JLabel labelLogin;
	private JPanel frameLogin;

	public GUILogin(JPanel registro) {
		this.crearGUI();
		this.panelLogin.add(registro, "name_242663905311000");
	}

	private void crearGUI() {
		panelLogin = new JPanel();
		panelLogin.setLayout(new CardLayout(0, 0));
		frameLogin = new JPanel();
		frameLogin.setBorder(new LineBorder(new Color(0, 0, 0)));
		frameLogin.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("60px"),
						ColumnSpec.decode("87px"), ColumnSpec.decode("33px"), ColumnSpec.decode("55px"),
						ColumnSpec.decode("25px"), ColumnSpec.decode("95px"), ColumnSpec.decode("default:grow"), },
				new RowSpec[] { RowSpec.decode("fill:default:grow"), RowSpec.decode("30px"), RowSpec.decode("25px"),
						RowSpec.decode("37px"), RowSpec.decode("20px"), RowSpec.decode("40px"), RowSpec.decode("20px"),
						RowSpec.decode("39px"), RowSpec.decode("23px"), RowSpec.decode("37px"), RowSpec.decode("23px"),
						RowSpec.decode("fill:default:grow"), }));

		labelLogin = new JLabel("Login");
		labelLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelLoginIncorrecto = new JLabel("");
		labelLoginIncorrecto.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelLoginIncorrecto.setForeground(new Color(255, 0, 0));

		valorUsuario = new JTextField();
		valorPassword = new JPasswordField();
		valorUsuario.setToolTipText("Usuario");
		valorUsuario.setColumns(10);

		btnRegistrarse = new JButton("Registrarse");
		btnLogin = new JButton("Iniciar Sesion");
		btnRecuperarPassword = new JButton("Recuperar Password");
		btnRecuperarPassword.setForeground(new Color(26, 95, 180));
		btnRecuperarPassword.setBackground(new Color(246, 245, 244));

		frameLogin.add(labelLogin, "5, 3, center, center");
		frameLogin.add(labelLoginIncorrecto, "3, 4, 5, 1, center, center");
		frameLogin.add(valorUsuario, "3, 5, 5, 1, fill, center");
		frameLogin.add(valorPassword, "3, 7, 5, 1, fill, center");
		frameLogin.add(btnRegistrarse, "3, 9, 2, 1, fill, center");
		frameLogin.add(btnLogin, "6, 9, 2, 1, fill, center");
		frameLogin.add(btnRecuperarPassword, "3, 11, 5, 1, center, center");
		
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarRegistro();
			}
		});

		panelLogin.add(frameLogin, "name_242549581087700");
	}
	
	public JPanel getPanel() {
		return this.panelLogin;
	}

	public void asignarAccionBtnLogin(ActionListener accion) {
		this.btnLogin.addActionListener(accion);
	}
	
	public void asignarAccionBtnRecuperarPassword(ActionListener accion) {
		this.btnRecuperarPassword.addActionListener(accion);
	}

	public String getUsuario() {
		return this.valorUsuario.getText();
	}

	public String getPassword() {
		return this.valorPassword.getText();
	}

	public void setMensajeLogin(String mensaje) {
		this.labelLoginIncorrecto.setText(mensaje);
	}
	
	public void mostrarRegistro() {
		((CardLayout) panelLogin.getLayout()).last(panelLogin);
	}
	
	public void ocultarRegistro() {
		((CardLayout) panelLogin.getLayout()).first(panelLogin);
	}
	
	public void setUsuario(String usuario) {
		this.valorUsuario.setText(usuario);
	}
	
	public void setPassword(String password) {
		this.valorPassword.setText(password);
	}

}
