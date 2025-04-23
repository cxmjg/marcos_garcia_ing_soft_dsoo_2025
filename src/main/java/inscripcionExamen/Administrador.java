package inscripcionExamen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Administrador implements Sesion {
	private Usuario usuario;
	private Conexion base;
	private GUIAdministrador interfaz;

	public Administrador() {
		interfaz = new GUIAdministrador();
		// Administracion
		// Interfaz principal
		interfaz.setAccionCambiarTab(completarTablas());

		// Tab Usuario
		interfaz.asignarAccionBtnModificarUsuario(completarDatosUsuarioSeleccionado());
		interfaz.asignarAccionBtnGuardarUsuario(guardarUsuario());
		interfaz.asignarAccionBtnDeshabilitarUsuario(deshabilitarUsuario());

		// Tab Mesa
		interfaz.asignarAccionBtnModificarMesa(completarDatosMesaSeleccionada());
		interfaz.asignarAccionBtnGuardarMesa(guardarMesa());
		interfaz.asignarAccionBtnEliminarMesa(deshabilitarMesa());

		// Tab Examen
		interfaz.asignarAccionBtnModificarExamen(completarDatosExamenSeleccionado());
		interfaz.asignarAccionBtnGuardarExamen(guardarExamen());
		interfaz.asignarAccionBtnEliminarExamen(deshabilitarExamen());

		// Tab Materia
		interfaz.asignarAccionBtnModificarMateria(completarDatosMateriaSeleccionada());
		interfaz.asignarAccionBtnGuardarMateria(guardarMateria());
		interfaz.asignarAccionBtnEliminarMateria(deshabilitarMateria());
	}

	@Override
	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Component getPanel() {
		return interfaz.getPanel();
	}

	@Override
	public void setConexion(Conexion base) {
		this.base = base;
		interfaz.completarTablaMesas(base.getMesas());
	}

	public static String dateAString(Date date) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.format(date);
		} catch (Exception e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

	public ChangeListener completarTablas() {
		return new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				String tab = sourceTabbedPane.getTitleAt(index);
				if (tab == "Usuarios") {
					interfaz.vaciarRolesModificarUsuario();
					interfaz.completarTablaUsuarios(base.getUsuarios());
					interfaz.setRolesModificarUsuario(base.getRoles());
				}
				if (tab == "Mesas") {
					interfaz.completarTablaMesas(base.getMesas());
				}

				if (tab == "Examenes") {
					interfaz.completarTablaExamenes(base.getExamenes());
					interfaz.setDocentePresidenteModificarExamen(base.getDocentes());
					interfaz.setDocenteVocalModificarExamen(base.getDocentes());
					interfaz.setMateriasModificarExamen(base.getMateriasHabilitadas());
					interfaz.setMesasModificarExamen(base.getMesasHabilitadas());
				}

				if (tab == "Materias") {
					interfaz.completarTablaMaterias(base.getMaterias());
				}
			}
		};

	}

	// Funciones Usuario

	public ActionListener deshabilitarUsuario() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = base.getUsuarioById(interfaz.getUsuarioSeleccionadoId());
				base.setHabilitadoUsuario(usuario.getId(), false);
				interfaz.completarTablaUsuarios(base.getUsuarios());
			}
		};
	}

	public ActionListener completarDatosUsuarioSeleccionado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = base.getUsuarioById(interfaz.getUsuarioSeleccionadoId());
				int rol = 0;
				if (usuario.getRol() != null) {
					rol = usuario.getRol().getId();
				}
				interfaz.setNombreModificarUsuario(usuario.getNombre());
				interfaz.setApellidoModificarUsuario(usuario.getApellido());
				interfaz.setEmailModificarUsuario(usuario.getEmail());
				interfaz.setPasswordModificarUsuario(usuario.getContrasena());
				interfaz.setHabilitadoModificarUsuario(usuario.isHabilitado());
				interfaz.setRolModificarUsuario(rol);
				interfaz.visibilidadPanelModificacionUsuario(true);
			}
		};
	}

	public ActionListener guardarUsuario() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!interfaz.camposCompletosAdminUsuario()) {

					return;
				}

				int id = interfaz.getUsuarioSeleccionadoId();
				if (id != -1) {
					base.setNombreUsuario(id, interfaz.getNombreModificarUsuario());
					base.setApellidoUsuario(id, interfaz.getApellidoModificarUsuario());
					base.setEmailUsuario(id, interfaz.getEmailModificarUsuario());
					base.setPasswordUsuario(id, interfaz.getPasswordModificarUsuario());
					base.setRolUsuario(id, base.getRolById(interfaz.getRolModificarUsuario()));
					base.setHabilitadoUsuario(id, interfaz.getHabilitadoModificarUsuario());
				} else {
					base.crearUsuario(interfaz.getNombreModificarUsuario(), interfaz.getApellidoModificarUsuario(),
							interfaz.getEmailModificarUsuario(), interfaz.getPasswordModificarUsuario(),
							interfaz.getRolModificarUsuario(), interfaz.getHabilitadoModificarUsuario());
				}
				interfaz.completarTablaUsuarios(base.getUsuarios());
			}
		};
	}

	// Funciones Mesa

	public ActionListener completarDatosMesaSeleccionada() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mesa mesa = base.getMesaById(interfaz.getMesaSeleccionadaId());
				interfaz.setFechaInicioMesa(dateAString(mesa.getFechaInicio()));
				interfaz.setFechaFinMesa(dateAString(mesa.getFechaFin()));
				interfaz.setFechaInicioInscripcionMesa(dateAString(mesa.getFechaInicioInscripcion()));
				interfaz.setFechaFinInscripcionMesa(dateAString(mesa.getFechaFinInscripcion()));
				interfaz.visibilidadPanelModificacionMesa(true);
			}
		};
	}

	public ActionListener guardarMesa() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!interfaz.camposCompletosModificacionMesa()) {
					return;
				}

				int id = interfaz.getMesaSeleccionadaId();
				if (id != -1) {

					base.setFechaInicioMesa(id, interfaz.getFechaInicioMesa());
					base.setFechaFinMesa(id, interfaz.getFechaFinMesa());
					base.setFechaInicioInscripcionMesa(id, interfaz.getFechaInicioInscripcionMesa());
					base.setFechaFinInscripcionMesa(id, interfaz.getFechaFinInscripcionMesa());
					interfaz.completarTablaMesas(base.getMesas());
				} else {

					base.crearMesa(interfaz.getFechaInicioMesa(), interfaz.getFechaFinMesa(),
							interfaz.getFechaInicioInscripcionMesa(), interfaz.getFechaFinInscripcionMesa());
				}

				interfaz.completarTablaMesas(base.getMesas());
			}
		};
	}

	public ActionListener deshabilitarMesa() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mesa mesa = base.getMesaById(interfaz.getMesaSeleccionadaId());
				base.setHabilitadoMesa(mesa.getId(), false);
				interfaz.completarTablaMesas(base.getMesas());
			}
		};
	}
	
	

	// Funciones Examen

	public ActionListener completarDatosExamenSeleccionado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Examen examen = base.getExamenById(interfaz.getExamenSeleccionadoId());
				interfaz.setFechaExamen(dateAString(examen.getFecha()));
				interfaz.setMesaExamen(examen.getMesa());
				interfaz.setPresidenteExamen(examen.getDocenteTitular());
				interfaz.setVocalExamen(examen.getDocenteVocal());
				interfaz.setMateriaExamen(examen.getMateria());
				interfaz.visibilidadPanelModificacionExamen(true);
			}
		};
	}

	public ActionListener deshabilitarExamen() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Examen examen = base.getExamenById(interfaz.getExamenSeleccionadoId());
				base.setHabilitadoExamen(examen.getId(), false);
				interfaz.completarTablaExamenes(base.getExamenes());
			}
		};
	}

	public ActionListener guardarExamen() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!interfaz.camposCompletosAdminExamen()) {

					return;
				}

				int id = interfaz.getExamenSeleccionadoId();
				if (id != -1) {
					base.setFechaExamen(id, interfaz.getFechaExamen());
					base.setMateriaExamen(id, base.getMateriaById(interfaz.getMateriaIdExamen()));
					base.setMesaExamen(id, base.getMesaById(interfaz.getMesaIdExamen()));
					base.setPresidenteExamen(id, base.getUsuarioById(interfaz.getPresidenteExamen()));
					base.setVocalExamen(id, base.getUsuarioById(interfaz.getVocalExamen()));
				} else {
					base.crearExamen(interfaz.getFechaExamen(), true,
							base.getUsuarioById(interfaz.getPresidenteExamen()),
							base.getUsuarioById(interfaz.getVocalExamen()),
							base.getMateriaById(interfaz.getMateriaIdExamen()),
							base.getMesaById(interfaz.getMesaIdExamen()));
				}
				interfaz.completarTablaExamenes(base.getExamenes());
			}
		};
	}

	// Funciones Materia

	public ActionListener completarDatosMateriaSeleccionada() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Materia materia = base.getMateriaById(interfaz.getMateriaSeleccionadaId());
				interfaz.editarMateria(materia);
			}
		};
	}

	public ActionListener guardarMateria() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!interfaz.camposCompletosModificacionMateria()) {
					return;
				}

				int id = interfaz.getMateriaSeleccionadaId();
				if (id != -1) {

					base.setNombreMateria(id, interfaz.getNombreMateria());
				} else {

					base.crearMateria(interfaz.getNombreMateria());
				}

				interfaz.completarTablaMaterias(base.getMaterias());
			}
		};
	}

	public ActionListener deshabilitarMateria() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Materia materia = base.getMateriaById(interfaz.getMateriaSeleccionadaId());
				base.setHabilitadoMateria(materia.getId(), false);
				interfaz.completarTablaMaterias(base.getMaterias());
			}
		};
	}

	@Override
	public InscripcionExamen getInscripcionExamen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInscripcionExamen(InscripcionExamen inscripcion) {
		// TODO Auto-generated method stub

	}

	@Override
	public InscripcionMateria getInscripcionMateria() {
		// TODO Auto-generated method stub
		return null;
	}

}
