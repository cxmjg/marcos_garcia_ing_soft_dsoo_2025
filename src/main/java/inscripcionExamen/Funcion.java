package inscripcionExamen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jakarta.transaction.Transactional;

public class Funcion {
	private GUI interfaz;
	private Conexion base;

	public Funcion(GUI interfaz, Conexion base) {
		this.interfaz = interfaz;
		this.base = base;

	}
	
	public ActionListener login() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = interfaz.getUsuarioLogin();
				String password = interfaz.getPasswordLogin();
				Usuario usuarioEncontrado = base.validarUsuario(email, password);
				int rol = -1; // Usuario no existe o contraseña incorrecta
				if (usuarioEncontrado != null) {
					if (usuarioEncontrado.getContrasena().equals(password)) {
						if (!usuarioEncontrado.isHabilitado()) {
							rol = 0; // Usuario deshabilitado
						} else {
							rol = usuarioEncontrado.getRol().getId();
						}
					} else {
						rol = -2; // Usuario existe pero password incorrecta
					}
				}

				if (rol >= 0) {
					if (rol == 0) {
						interfaz.setMensajeLogin("El usuario esta deshabilitado, contacte a un Administrador");
					} else {
						String nombreRol = usuarioEncontrado.getRol().getNombre();
						Sesion sesion = new Factoria().iniciarSesion(usuarioEncontrado, nombreRol);
						sesion.setConexion(base);
						Component panel = sesion.getPanel();
						interfaz.iniciarSesion(panel);
					}

				} else {
					interfaz.setMensajeLogin("Usuario y/o Contraseña incorrectos");
				}

			}
		};
	}

	public ActionListener crearRegistro() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (interfaz.verificarCamposRequeridosNuevousuario()) {
					String nombre = interfaz.getNombreNuevoUsuario();
					String apellido = interfaz.getApellidoNuevoUsuario();
					String email = interfaz.getEmailNuevoUsuario();
					String password = interfaz.getPasswordNuevoUsuario();
					Usuario usuarioEncontrado = base.validarUsuario(email, password);
					if (usuarioEncontrado == null) {
						base.crearUsuario(nombre, apellido, email, password, 0, false);
						interfaz.setMensajeRegistro(
								"Usuario creado con exito, contacte a un Administrador para la habilitacion");
						interfaz.traeLimpiaRegistro();
					} else {
						
						interfaz.setMensajeRegistro(
								"El usuario ya existe");
					}
				}

			}
		};
	}



}
