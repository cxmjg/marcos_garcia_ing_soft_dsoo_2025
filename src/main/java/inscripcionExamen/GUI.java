package inscripcionExamen;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.Font;

public class GUI {
	private JFrame frame;
	private JMenuItem menuItemSalir;
	private JMenuItem menuItemAcerca;

	// Login
	private JPanel panelLogin;
	private GUILogin login;

	// Registro
	private JPanel panelRegistro;
	private GUIRegistro registro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setVisible(true);
		frame.setBounds(100, 100, 839, 642);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu menuOpciones = new JMenu("Opciones");
		menuOpciones.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(menuOpciones);
		menuItemSalir = new JMenuItem("Salir");
		menuOpciones.add(menuItemSalir);
		JSeparator separator = new JSeparator();
		menuOpciones.add(separator);
		menuItemAcerca = new JMenuItem("Acerca");
		menuOpciones.add(menuItemAcerca);
		menuItemSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarSesion();
			}
		});
		menuItemAcerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verAcercaDe();
			}
		});

		// Registro
		registro = new GUIRegistro();
		panelRegistro = registro.getPanel();

		// Login
		login = new GUILogin(panelRegistro);
		panelLogin = login.getPanel();
		registro.setLogin(login);
		login.asignarAccionBtnRecuperarPassword(this.verRecuperarPassword());

		// Panel Administracion
		// MySingleSelectionModel selectionModel = new MySingleSelectionModel(frame);
		// panelAdministracion.setModel(selectionModel);

		frame.getContentPane().add(panelLogin, "panelLogin");
		SwingUtilities.updateComponentTreeUI(frame);

	}

	public void verAcercaDe() {
		String texto = leerArchivo("acerca.txt");
		ImageIcon icono = new ImageIcon("imagenes/logo.png");
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		JLabel imageLabel = new JLabel(icono);
		JLabel textLabel = new JLabel("<html><p style='width:400px;'>" + texto + "</p></html>");
		textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		textLabel.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(imageLabel, BorderLayout.WEST);
		panel.add(textLabel, BorderLayout.CENTER);
		JOptionPane.showMessageDialog(null, panel, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
	}

	public ActionListener verRecuperarPassword() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String texto = "Para recuperar tu password contacta con un Administrador.";
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout(10, 10));
				JLabel textLabel = new JLabel("<html><p style='width:400px;'>" + texto + "</p></html>");
				textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
				textLabel.setVerticalAlignment(SwingConstants.CENTER);
				panel.add(textLabel, BorderLayout.CENTER);
				JOptionPane.showMessageDialog(null, panel, "Recuperar Password", JOptionPane.INFORMATION_MESSAGE);
			}
		};
	}

	// Login
	public void iniciarSesion(Component panel) {
		login.setMensajeLogin("");
		frame.getContentPane().add(panel, "Panel");
		((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Panel");

	}

	public void cerrarSesion() {
		((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "panelLogin");
		login.setUsuario("");
		login.setPassword("");
	}

	public void asignarAccionBtnLogin(ActionListener accion) {
		login.asignarAccionBtnLogin(accion);
	}

	public void setMensajeLogin(String texto) {
		login.setMensajeLogin(texto);
	}

	public String getUsuarioLogin() {
		return login.getUsuario();
	}

	public String getPasswordLogin() {
		return login.getPassword();
	}

	// Registro

	public void traeLimpiaRegistro() {
		registro.limpiaRegistro();
	}

	public void asignarAccionBtnGuardarNuevoUsuario(ActionListener accion) {
		registro.asignarAccionBtnGuardarNuevoUsuario(accion);
	}

	public Boolean verificarCamposRequeridosNuevousuario() {
		return registro.verificarCamposRequeridosNuevousuario();
	}

	public String getNombreNuevoUsuario() {
		return registro.getNombreNuevoUsuario();
	}

	public String getApellidoNuevoUsuario() {
		return registro.getApellidoNuevoUsuario();
	}

	public String getEmailNuevoUsuario() {
		return registro.getEmailNuevoUsuario();
	}

	public String getPasswordNuevoUsuario() {
		return registro.getPasswordNuevoUsuario();
	}

	public void setMensajeRegistro(String texto) {
		registro.setMensajeRegistro(texto);
	}

	public String leerArchivo(String archivo) {
		String texto = "";
		try {
			File myObj = new File(archivo);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				texto += myReader.nextLine() + "\n";
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo leer el archivo: " + archivo);
			e.printStackTrace();
		}
		return texto;
	}
}
