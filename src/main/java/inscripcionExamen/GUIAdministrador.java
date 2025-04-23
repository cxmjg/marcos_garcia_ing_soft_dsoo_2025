package inscripcionExamen;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Dimension;


public class GUIAdministrador {
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	// Panel Administracion
	private JTabbedPane panelAdministracion;

	// Administracion Mesas
	private JPanel panelEdicionMesa = new JPanel();
	private JButton btnModificarMesa;
	private JButton btnGuardarMesa;
	private JButton btnEliminarMesa;
	private JFormattedTextField valorFechaInicioMesa = new JFormattedTextField(dateFormat);
	private JFormattedTextField valorFechaFinMesa = new JFormattedTextField(dateFormat);
	private JFormattedTextField valorFechaInicioInscripcionMesa = new JFormattedTextField(dateFormat);
	private JFormattedTextField valorFechaFinInscripcionMesa = new JFormattedTextField(dateFormat);
	private JTable tablaMesas;

	// Administracion Examenes
	private JPanel panelEdicionExamen = new JPanel();
	private JButton btnEliminarExamen;
	private JButton btnModificarExamen;
	private JButton btnNuevoExamen;
	private JButton btnCancelarExamen;
	private JButton btnGuardarExamen;
	private JFormattedTextField valorFechaExamen = new JFormattedTextField(dateFormat);
	private JComboBox valorMateriaExamen = new JComboBox();
	private JComboBox valorMesaExamen = new JComboBox();
	private JComboBox valorDocentePresidente = new JComboBox();
	private JComboBox valorDocenteVocal = new JComboBox();
	private JTable tablaExamenes;

	// Administracion Materias
	private JPanel panelEdicionMateria;
	private JButton btnEliminarMateria;
	private JButton btnModificarMateria;
	private JButton btnNuevaMateria;
	private JButton btnCancelarMateria;
	private JButton btnGuardarMateria;
	private JTextField valorNombreNuevaMateria;

	// Administracion Usuarios
	private JPanel panelEdicionUsuario;
	private JButton btnAdmDeshabilitarUsuario;
	private JButton btnAdmModificarUsuario;
	private JButton btnAdmGuardarNuevoUsuario;
	private JButton btnAdmNuevoUsuario;
	private JButton btnAdmCancelarNuevoUsuario;
	private JTextField valorAdmNombreNuevoUsuario;
	private JTextField valorAdmApellidoNuevoUsuario;
	private JPasswordField valorAdmPasswordNuevoUsuario;
	private JCheckBox valorAdmHabilitadoNuevoUsuario;
	private JFormattedTextField valorAdmEmailNuevoUsuario;
	private JComboBox valorAdmRolNuevoUsuario;
	private JTable tablaUsuarios;
	private JTable tablaMaterias;
	private JTable tablaAsistenciaDocente;
	private MySingleSelectionModel selectionModel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAdministrador window = new GUIAdministrador();
					window.getPanel().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIAdministrador() {

		// Panel Administracion
		
		selectionModel = new MySingleSelectionModel();

		panelAdministracion = new JTabbedPane(JTabbedPane.TOP);
		panelAdministracion.setMinimumSize(new Dimension(800, 600));
		panelAdministracion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelAdministracion.setModel(selectionModel);

		// Administracion Mesas
		JPanel panelMesas = this.crearAdministracionMesas();
		panelAdministracion.addTab("Mesas", null, panelMesas, null);

		// Administracion Examenes
		JPanel panelExamenes = this.crearAdministracionExamenes();
		panelAdministracion.addTab("Examenes", null, panelExamenes, null);

		// Administracion Materias
		JPanel panelMaterias = this.crearAdministracionMaterias();
		panelAdministracion.addTab("Materias", null, panelMaterias, null);

		// Administracion Usuarios
		JPanel panelUsuarios = this.crearAdministracionUsuarios();
		panelAdministracion.addTab("Usuarios", null, panelUsuarios, null);
	}
	
	public void setSelectionGui(Component gui) {
		this.selectionModel.setGui(gui);
	}

	public JTabbedPane getPanel() {
		return panelAdministracion;
	}
	// Herramientas

	public int getSelectedIdFromCombo(JComboBox combo) {
		String valor = combo.getSelectedItem().toString();
		return Integer.parseInt(valor.substring(0, valor.indexOf(" - ")));
	}

	public void setComboByText(JComboBox combo, String text) {
		int items = combo.getItemCount();
		for (int i = 0; i < items; i++) {
			String valor = combo.getItemAt(i).toString();
			if (valor.equals(text)) {
				combo.setSelectedIndex(i);
			}
		}
	}

	public String dateAString(Date date, Boolean hora) {
		DateFormat formatter;
		try {
			if (hora) {
				formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			} else {
				formatter = new SimpleDateFormat("dd-MM-yyyy");
			}
			return formatter.format(date);
		} catch (Exception e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

	public void eliminarTab(String nombreTab) {
		this.panelAdministracion.removeTabAt(panelAdministracion.indexOfTab(nombreTab));
	}

	public void setAccionCambiarTab(ChangeListener changeListener) {
		panelAdministracion.addChangeListener(changeListener);
	}

	private static class MySingleSelectionModel extends DefaultSingleSelectionModel {
		private Component gui;
		private boolean activated = false;

		public MySingleSelectionModel() {
			
		}
		
		public void setGui(Component gui) {
			this.gui = gui;
		}

		public void setActivated(boolean activated) {
			this.activated = activated;
		}

		@Override
		public void setSelectedIndex(int index) {
			if (activated) {
				String text = String.format("Before change, old index: %d; new index: %d", super.getSelectedIndex(),
						index);
				JOptionPane.showMessageDialog(gui, text);
			}
			super.setSelectedIndex(index);
		}
	}
	// Metodos Administracion Materias

	public boolean camposCompletosModificacionMateria() {

		if (getNombreMateria().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre de la materia es requerido", "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void visibilidadPanelModificacionMateria(Boolean visible) {
		panelEdicionMateria.setVisible(visible);
	}

	public void cancelarEdicionMateria() {
		this.valorNombreNuevaMateria.setText(null);
		visibilidadPanelModificacionMateria(false);
	}

	public void editarMateria(Materia materia) {
		this.valorNombreNuevaMateria.setText(materia.getNombre());
		visibilidadPanelModificacionMateria(true);
	}

	public String getNombreMateria() {
		return this.valorNombreNuevaMateria.getText();
	}

	public void asignarAccionBtnModificarMateria(ActionListener accion) {
		this.btnModificarMateria.addActionListener(accion);
	}

	public void asignarAccionBtnEliminarMateria(ActionListener accion) {
		this.btnEliminarMateria.addActionListener(accion);
	}

	public void asignarAccionBtnGuardarMateria(ActionListener accion) {
		this.btnGuardarMateria.addActionListener(accion);
	}

	public DefaultTableModel completarCabeceraTablaMaterias() {
		String[] columnNames = { "Id", "Nombre", "Habilitado", "Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		this.tablaMaterias.setModel(table_model);
		return table_model;
	}

	public void completarTablaMaterias(List<Materia> materias) {
		DefaultTableModel table_model = completarCabeceraTablaMaterias();
		if (materias != null) {
			for (Materia materia : materias) {
				table_model.addRow(new Object[] { materia.getId(), materia.getNombre(), materia.getHabilitado(),
						this.dateAString(materia.getFechaCreacion(), true) });
			}
			this.tablaMaterias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					btnModificarMateria.setEnabled(true);
					btnEliminarMateria.setEnabled(true);
					cancelarEdicionMateria();
				}
			});
		}

	}

	public int getMateriaSeleccionadaId() {
		int row = this.tablaMaterias.getSelectedRow();
		if (row != -1) {
			return (int) this.tablaMaterias.getValueAt(this.tablaMaterias.getSelectedRow(), 0);
		}
		return row;
	}

	// Metodos Administracion Usuarios

	public boolean camposCompletosAdminUsuario() {

		if (getNombreModificarUsuario().isEmpty() || getApellidoModificarUsuario().isEmpty()
				|| getEmailModificarUsuario().isEmpty() || getPasswordModificarUsuario().isEmpty()
				|| getRolModificarUsuario() == -1) {
			JOptionPane.showMessageDialog(null, "Todos los campos son requeridos", "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void visibilidadPanelModificacionUsuario(Boolean visible) {
		panelEdicionUsuario.setVisible(visible);
	}

	public void cancelarEdicionUsuario() {
		this.valorAdmNombreNuevoUsuario.setText(null);
		this.valorAdmApellidoNuevoUsuario.setText(null);
		this.valorAdmEmailNuevoUsuario.setText(null);
		this.valorAdmPasswordNuevoUsuario.setText(null);
		this.valorAdmRolNuevoUsuario.setSelectedIndex(-1);
		this.valorAdmHabilitadoNuevoUsuario.setSelected(false);
		visibilidadPanelModificacionUsuario(false);
	}

	public DefaultTableModel completarCabeceraTablaUsuarios() {
		String[] columnNames = { "Id", "Nombre", "Apellido", "Email", "Rol", "Habilitado", "Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		this.tablaUsuarios.setModel(table_model);
		return table_model;
	}

	public void completarTablaUsuarios(List<Usuario> usuarios) {
		DefaultTableModel table_model = completarCabeceraTablaUsuarios();
		for (Usuario usuario : usuarios) {
			String rol = "";
			if (usuario.getRol() != null) {
				rol = usuario.getRol().getNombre();
			}
			table_model.addRow(
					new Object[] { usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), rol,
							usuario.isHabilitado(), this.dateAString(usuario.getFechaCreacion(), true) });
		}

		this.tablaUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				btnAdmDeshabilitarUsuario.setEnabled(true);
				btnAdmModificarUsuario.setEnabled(true);
				cancelarEdicionUsuario();
			}
		});
	}

	public void asignarAccionBtnModificarUsuario(ActionListener accion) {
		this.btnAdmModificarUsuario.addActionListener(accion);
	}

	public void asignarAccionBtnDeshabilitarUsuario(ActionListener accion) {
		this.btnAdmDeshabilitarUsuario.addActionListener(accion);
	}

	public void setNombreModificarUsuario(String Nombre) {
		this.valorAdmNombreNuevoUsuario.setText(Nombre);
	}

	public String getNombreModificarUsuario() {
		return this.valorAdmNombreNuevoUsuario.getText();
	}

	public void setApellidoModificarUsuario(String apellido) {
		this.valorAdmApellidoNuevoUsuario.setText(apellido);
	}

	public String getApellidoModificarUsuario() {
		return this.valorAdmApellidoNuevoUsuario.getText();
	}

	public void setEmailModificarUsuario(String email) {
		this.valorAdmEmailNuevoUsuario.setText(email);
	}

	public String getEmailModificarUsuario() {
		return this.valorAdmEmailNuevoUsuario.getText();
	}

	public void setPasswordModificarUsuario(String password) {
		this.valorAdmPasswordNuevoUsuario.setText(password);
	}

	public String getPasswordModificarUsuario() {
		return this.valorAdmPasswordNuevoUsuario.getText();
	}

	public void setHabilitadoModificarUsuario(Boolean habilitado) {
		this.valorAdmHabilitadoNuevoUsuario.setSelected(habilitado);
	}

	public Boolean getHabilitadoModificarUsuario() {
		return this.valorAdmHabilitadoNuevoUsuario.isSelected();
	}

	public void setRolesModificarUsuario(List<Rol> roles) {
		for (Rol rol : roles) {
			this.valorAdmRolNuevoUsuario.addItem(rol.getNombre());
		}
	}

	public int getRolModificarUsuario() {
		return this.valorAdmRolNuevoUsuario.getSelectedIndex() + 1;
	}

	public void vaciarRolesModificarUsuario() {
		this.valorAdmRolNuevoUsuario.removeAllItems();
	}

	public void setRolModificarUsuario(int rol) {
		this.valorAdmRolNuevoUsuario.setSelectedIndex(rol - 1);
	}

	public int getUsuarioSeleccionadoId() {
		int row = this.tablaUsuarios.getSelectedRow();
		if (row != -1) {
			return (int) this.tablaUsuarios.getValueAt(this.tablaUsuarios.getSelectedRow(), 0);
		}
		return row;
	}

	public void asignarAccionBtnGuardarUsuario(ActionListener accion) {
		this.btnAdmGuardarNuevoUsuario.addActionListener(accion);
	}

	// Metodos Administracion Examenes

	public boolean camposCompletosAdminExamen() {

		if (getFechaExamen().isEmpty() || getMateriaIdExamen() == -1 || getMesaIdExamen() == -1
				|| getPresidenteExamen() == -1 || getVocalExamen() == -1) {

			JOptionPane.showMessageDialog(null, "Todos los campos son requeridos", "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void visibilidadPanelModificacionExamen(Boolean visible) {
		panelEdicionExamen.setVisible(visible);
	}

	public void cancelarEdicionExamen() {
		this.valorMateriaExamen.setSelectedIndex(-1);
		this.valorFechaExamen.setText(null);
		this.valorMesaExamen.setSelectedIndex(-1);
		this.valorDocentePresidente.setSelectedIndex(-1);
		this.valorDocenteVocal.setSelectedIndex(-1);
		visibilidadPanelModificacionExamen(false);
	}

	public void editarExamen(String fechaInicioMesa, String fechaFinMesa, String fechaInicioInscripcion,
			String fechaFinInscripcion) {
		this.valorFechaInicioMesa.setValue(fechaInicioMesa);
		this.valorFechaFinMesa.setValue(fechaFinMesa);
		this.valorFechaInicioInscripcionMesa.setValue(fechaInicioInscripcion);
		this.valorFechaFinInscripcionMesa.setValue(fechaFinInscripcion);
		visibilidadPanelModificacionExamen(true);
	}

	public String getFechaExamen() {
		return this.valorFechaExamen.getText();
	}

	public void setFechaExamen(String fecha) {
		this.valorFechaExamen.setText(fecha);
	}

	public int getMateriaIdExamen() {
		return getSelectedIdFromCombo(this.valorMateriaExamen);
	}

	public void setMateriaExamen(Materia materia) {
		setComboByText(this.valorMateriaExamen, materia.getId() + " - " + materia.getNombre());
	}

	public int getPresidenteExamen() {
		return getSelectedIdFromCombo(this.valorDocentePresidente);
	}

	public void setPresidenteExamen(Usuario presidente) {
		setComboByText(this.valorDocentePresidente,
				presidente.getId() + " - " + presidente.getNombre() + " " + presidente.getApellido());
	}

	public int getVocalExamen() {
		return getSelectedIdFromCombo(this.valorDocenteVocal);
	}

	public void setVocalExamen(Usuario vocal) {
		setComboByText(this.valorDocenteVocal, vocal.getId() + " - " + vocal.getNombre() + " " + vocal.getApellido());
	}

	public int getMesaIdExamen() {
		return getSelectedIdFromCombo(this.valorMesaExamen);
	}

	public void setMesaExamen(Mesa mesa) {
		setComboByText(this.valorMesaExamen, mesa.getId() + " - " + this.dateAString(mesa.getFechaInicio(), false)
				+ " / " + this.dateAString(mesa.getFechaFin(), false));
	}

	public void asignarAccionBtnModificarExamen(ActionListener accion) {
		this.btnModificarExamen.addActionListener(accion);
	}

	public void asignarAccionBtnEliminarExamen(ActionListener accion) {
		this.btnEliminarExamen.addActionListener(accion);
	}

	public void asignarAccionBtnGuardarExamen(ActionListener accion) {
		this.btnGuardarExamen.addActionListener(accion);
	}

	public int getExamenSeleccionadoId() {
		int row = this.tablaExamenes.getSelectedRow();
		if (row != -1) {
			return (int) this.tablaExamenes.getValueAt(this.tablaExamenes.getSelectedRow(), 0);
		}
		return row;
	}

	public void setMesasModificarExamen(List<Mesa> mesas) {
		for (Mesa mesa : mesas) {
			this.valorMesaExamen.addItem(mesa.getId() + " - " + this.dateAString(mesa.getFechaInicio(), false) + " / "
					+ this.dateAString(mesa.getFechaFin(), false));
		}
	}

	public void setDocentePresidenteModificarExamen(List<Usuario> docentes) {
		for (Usuario docente : docentes) {
			this.valorDocentePresidente
					.addItem(docente.getId() + " - " + docente.getNombre() + " " + docente.getApellido());
		}
	}

	public void setDocenteVocalModificarExamen(List<Usuario> docentes) {
		for (Usuario docente : docentes) {
			this.valorDocenteVocal.addItem(docente.getId() + " - " + docente.getNombre() + " " + docente.getApellido());
		}
	}

	public void setMateriasModificarExamen(List<Materia> materias) {
		for (Materia materia : materias) {
			this.valorMateriaExamen.addItem(materia.getId() + " - " + materia.getNombre());
		}
	}

	public DefaultTableModel completarCabeceraTablaExamenes() {
		String[] columnNames = { "Id", "Fecha", "Mesa", "Materia", "Presidente", "Vocal", "Habilitado",
				"Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		this.tablaExamenes.setModel(table_model);
		return table_model;
	}

	public void completarTablaExamenes(List<Examen> examenes) {
		DefaultTableModel table_model = completarCabeceraTablaExamenes();
		if (examenes != null) {
			for (Examen examen : examenes) {
				table_model.addRow(new Object[] { examen.getId(), this.dateAString(examen.getFecha(), false),
						this.dateAString(examen.getMesa().getFechaInicio(), false) + " / "
								+ this.dateAString(examen.getMesa().getFechaFin(), false),
						examen.getMateria().getNombre(),
						examen.getDocenteTitular().getNombre() + " " + examen.getDocenteTitular().getApellido(),
						examen.getDocenteVocal().getNombre() + " " + examen.getDocenteVocal().getApellido(),
						examen.isHabilitado(), this.dateAString(examen.getFechaCreacion(), true) });
			}

			this.tablaExamenes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					btnEliminarExamen.setEnabled(true);
					btnModificarExamen.setEnabled(true);
					cancelarEdicionExamen();
				}
			});
		}

	}

	// Metodos Administracion Mesas

	public boolean camposCompletosModificacionMesa() {

		if (getFechaInicioMesa().isEmpty() || getFechaFinMesa().isEmpty() || getFechaInicioInscripcionMesa().isEmpty()
				|| getFechaFinInscripcionMesa().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Todos los campos de la mesa son requeridos", "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void visibilidadPanelModificacionMesa(Boolean visible) {
		panelEdicionMesa.setVisible(visible);
	}

	public void cancelarEdicionMesa() {
		this.valorFechaInicioMesa.setText(null);
		this.valorFechaFinMesa.setText(null);
		this.valorFechaInicioInscripcionMesa.setText(null);
		this.valorFechaFinInscripcionMesa.setText(null);
		visibilidadPanelModificacionMesa(false);
	}

	public void editarMesa(String fechaInicioMesa, String fechaFinMesa, String fechaInicioInscripcion,
			String fechaFinInscripcion) {
		this.valorFechaInicioMesa.setValue(fechaInicioMesa);
		this.valorFechaFinMesa.setValue(fechaFinMesa);
		this.valorFechaInicioInscripcionMesa.setValue(fechaInicioInscripcion);
		this.valorFechaFinInscripcionMesa.setValue(fechaFinInscripcion);
		visibilidadPanelModificacionMesa(true);
	}

	public DefaultTableModel completarCabeceraTablaMesas() {
		String[] columnNames = { "Id", "Fecha Inicio", "Fecha Fin", "Fecha de Inicio de Inscripcion",
				"Fecha de Fin de Inscripcion", "Habilitada", "Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		this.tablaMesas.setModel(table_model);
		return table_model;
	}

	public void completarTablaMesas(List<Mesa> mesas) {
		DefaultTableModel table_model = completarCabeceraTablaMesas();
		if (mesas != null) {
			for (Mesa mesa : mesas) {
				String rol = "";
				table_model.addRow(new Object[] { mesa.getId(), this.dateAString(mesa.getFechaInicio(), false),
						this.dateAString(mesa.getFechaFin(), false),
						this.dateAString(mesa.getFechaInicioInscripcion(), false),
						this.dateAString(mesa.getFechaFinInscripcion(), false), mesa.getHabilitado(),
						this.dateAString(mesa.getFechaCreacion(), true) });
			}
			this.tablaMesas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					btnEliminarMesa.setEnabled(true);
					btnModificarMesa.setEnabled(true);
					cancelarEdicionMesa();
				}
			});
		}

	}

	public String getFechaInicioMesa() {
		return this.valorFechaInicioMesa.getText();
	}

	public String getFechaFinMesa() {
		return this.valorFechaFinMesa.getText();
	}

	public String getFechaInicioInscripcionMesa() {
		return this.valorFechaInicioInscripcionMesa.getText();
	}

	public String getFechaFinInscripcionMesa() {
		return this.valorFechaFinInscripcionMesa.getText();
	}

	public void asignarAccionBtnEliminarMesa(ActionListener accion) {
		this.btnEliminarMesa.addActionListener(accion);
	}

	public void asignarAccionBtnModificarMesa(ActionListener accion) {
		this.btnModificarMesa.addActionListener(accion);
	}

	public void asignarAccionBtnGuardarMesa(ActionListener accion) {
		this.btnGuardarMesa.addActionListener(accion);
	}

	public int getMesaSeleccionadaId() {
		int row = this.tablaMesas.getSelectedRow();
		if (row != -1) {
			return (int) this.tablaMesas.getValueAt(this.tablaMesas.getSelectedRow(), 0);
		}
		return row;
	}

	public void setFechaInicioMesa(String fecha) {
		this.valorFechaInicioMesa.setText(fecha);
		;
	}

	public void setFechaFinMesa(String fecha) {
		this.valorFechaFinMesa.setText(fecha);
		;
	}

	public void setFechaInicioInscripcionMesa(String fecha) {
		this.valorFechaInicioInscripcionMesa.setText(fecha);
		;
	}

	public void setFechaFinInscripcionMesa(String fecha) {
		this.valorFechaFinInscripcionMesa.setText(fecha);
		;
	}

	private JPanel crearAdministracionMesas() {
		JPanel panelMesas = new JPanel();
		panelMesas.setLayout(new BoxLayout(panelMesas, BoxLayout.X_AXIS));
		JSplitPane splitPane = new JSplitPane();
		panelMesas.add(splitPane);
		JSplitPane panelAccionesMesa = new JSplitPane();
		panelAccionesMesa.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(panelAccionesMesa);
		JPanel panelOpcionesMesa = new JPanel();
		panelAccionesMesa.setLeftComponent(panelOpcionesMesa);
		panelOpcionesMesa.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("default:grow"),
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { RowSpec.decode("10dlu"), FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		JButton btnNuevaMesa = new JButton("Nueva Mesa");
		btnNuevaMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visibilidadPanelModificacionMesa(true);
			}
		});
		panelOpcionesMesa.add(btnNuevaMesa, "2, 2");
		btnModificarMesa = new JButton("Modificar Mesa");
		panelOpcionesMesa.add(btnModificarMesa, "2, 4");
		btnModificarMesa.setEnabled(false);
		btnEliminarMesa = new JButton("Eliminar Mesa");
		panelOpcionesMesa.add(btnEliminarMesa, "2, 6");
		btnEliminarMesa.setEnabled(false);
		JPanel panelEdicionMesaMaster = new JPanel();
		panelAccionesMesa.setRightComponent(panelEdicionMesaMaster);
		panelEdicionMesaMaster.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("250px"), },
				new RowSpec[] { RowSpec.decode("top:353px"), }));
		panelEdicionMesaMaster.add(panelEdicionMesa, "1, 1, center, top");
		panelEdicionMesa.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		JLabel labelFechaInicioMesa = new JLabel("Fecha Inicio de Mesa");
		labelFechaInicioMesa.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionMesa.add(labelFechaInicioMesa, "2, 4, 3, 1");
		valorFechaInicioMesa.setToolTipText("Ej: 23-10-2024");
		panelEdicionMesa.add(valorFechaInicioMesa, "2, 6, 3, 1, fill, default");
		JLabel labelFechaFinMesa = new JLabel("Fecha Fin de Mesa");
		labelFechaFinMesa.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionMesa.add(labelFechaFinMesa, "2, 10, 3, 1, center, default");
		valorFechaFinMesa.setToolTipText("Ej: 23-10-2024");
		panelEdicionMesa.add(valorFechaFinMesa, "2, 12, 3, 1, fill, default");
		JLabel labelFechaInicioInscripcionMesa = new JLabel("Fecha Inicio de Inscripcion");
		labelFechaInicioInscripcionMesa.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionMesa.add(labelFechaInicioInscripcionMesa, "2, 16, 3, 1");
		valorFechaInicioInscripcionMesa.setToolTipText("Ej: 23-10-2024");
		panelEdicionMesa.add(valorFechaInicioInscripcionMesa, "2, 18, 3, 1, fill, default");
		JLabel labelFechaFinInscripcionMesa = new JLabel("Fecha Fin de Inscripcion");
		labelFechaFinInscripcionMesa.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionMesa.add(labelFechaFinInscripcionMesa, "2, 22, 3, 1");
		panelEdicionMesa.add(valorFechaFinInscripcionMesa, "2, 24, 3, 1, fill, default");
		JButton btnCancelarMesa = new JButton("Cancelar");
		btnCancelarMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarEdicionMesa();
			}
		});
		panelEdicionMesa.add(btnCancelarMesa, "2, 28");
		btnGuardarMesa = new JButton("Guardar");
		btnGuardarMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelEdicionMesa.add(btnGuardarMesa, "4, 28");
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		tablaMesas = new JTable();
		scrollPane_1.setViewportView(tablaMesas);
		this.visibilidadPanelModificacionMesa(false);
		return panelMesas;

	}

	private JPanel crearAdministracionExamenes() {
		JPanel panelExamenes = new JPanel();
		panelExamenes.setLayout(new BoxLayout(panelExamenes, BoxLayout.X_AXIS));
		JSplitPane splitPaneExamenes = new JSplitPane();
		panelExamenes.add(splitPaneExamenes);
		JSplitPane panelAccionesExamen = new JSplitPane();
		panelAccionesExamen.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneExamenes.setLeftComponent(panelAccionesExamen);
		JPanel panelOpcionesExamen = new JPanel();
		panelAccionesExamen.setLeftComponent(panelOpcionesExamen);
		panelOpcionesExamen.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("default:grow"),
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { RowSpec.decode("10dlu"), FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		btnNuevoExamen = new JButton("Nuevo Examen");
		btnNuevoExamen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visibilidadPanelModificacionExamen(true);
			}
		});
		panelOpcionesExamen.add(btnNuevoExamen, "2, 2");
		btnModificarExamen = new JButton("Modificar Examen");
		btnModificarExamen.setEnabled(false);
		panelOpcionesExamen.add(btnModificarExamen, "2, 4");
		btnEliminarExamen = new JButton("Eliminar Examen");
		btnEliminarExamen.setEnabled(false);
		panelOpcionesExamen.add(btnEliminarExamen, "2, 6");
		JPanel panelEdicionExamenMaster = new JPanel();
		panelAccionesExamen.setRightComponent(panelEdicionExamenMaster);
		panelEdicionExamenMaster.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("250px"), },
				new RowSpec[] { RowSpec.decode("top:353px"), }));
		panelEdicionExamenMaster.add(panelEdicionExamen, "1, 1, center, top");
		panelEdicionExamen.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		JLabel labelMateriaDeExamen = new JLabel("Materia");
		labelMateriaDeExamen.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionExamen.add(labelMateriaDeExamen, "2, 4, 3, 1");
		panelEdicionExamen.add(valorMateriaExamen, "2, 6, 3, 1, fill, default");
		JLabel labelMesaExamen = new JLabel("Mesa");
		labelMesaExamen.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionExamen.add(labelMesaExamen, "2, 10, 3, 1");
		panelEdicionExamen.add(valorMesaExamen, "2, 12, 3, 1, fill, default");
		JLabel labelFechaExamen = new JLabel("Fecha de Examen");
		labelFechaExamen.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionExamen.add(labelFechaExamen, "2, 16, 3, 1, center, default");
		valorFechaExamen.setToolTipText("Ej: 23-10-2024");
		panelEdicionExamen.add(valorFechaExamen, "2, 18, 3, 1, fill, default");
		JLabel labelDocentePresidente = new JLabel("Presidente de Mesa");
		labelDocentePresidente.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionExamen.add(labelDocentePresidente, "2, 22, 3, 1");
		panelEdicionExamen.add(valorDocentePresidente, "2, 24, 3, 1, fill, default");
		JLabel labelFechaFinInscripcionExamen = new JLabel("Vocal");
		labelFechaFinInscripcionExamen.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionExamen.add(labelFechaFinInscripcionExamen, "2, 28, 3, 1");
		btnCancelarExamen = new JButton("Cancelar");
		btnCancelarExamen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarEdicionExamen();
			}
		});

		panelEdicionExamen.add(valorDocenteVocal, "2, 30, 3, 1, fill, default");
		panelEdicionExamen.add(btnCancelarExamen, "2, 34");
		btnGuardarExamen = new JButton("Guardar");
		panelEdicionExamen.add(btnGuardarExamen, "4, 34");
		JScrollPane scrollPane_2 = new JScrollPane();
		splitPaneExamenes.setRightComponent(scrollPane_2);
		tablaExamenes = new JTable();
		scrollPane_2.setViewportView(tablaExamenes);
		this.visibilidadPanelModificacionExamen(false);
		return panelExamenes;

	}

	private JPanel crearAdministracionMaterias() {
		JPanel panelMaterias = new JPanel();
		panelMaterias.setLayout(new BoxLayout(panelMaterias, BoxLayout.X_AXIS));
		JSplitPane splitPaneMaterias = new JSplitPane();
		panelMaterias.add(splitPaneMaterias);
		JSplitPane panelAccionesMateria = new JSplitPane();
		panelAccionesMateria.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneMaterias.setLeftComponent(panelAccionesMateria);
		JPanel panelOpcionesMateria = new JPanel();
		panelAccionesMateria.setLeftComponent(panelOpcionesMateria);
		panelOpcionesMateria.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("default:grow"),
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { RowSpec.decode("fill:10dlu"), RowSpec.decode("fill:default"),
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:default"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default"), FormSpecs.DEFAULT_ROWSPEC, }));
		btnNuevaMateria = new JButton("Nueva Materia");
		btnNuevaMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visibilidadPanelModificacionMateria(true);
			}

		});
		panelOpcionesMateria.add(btnNuevaMateria, "2, 2");
		btnModificarMateria = new JButton("Modificar Materia");
		panelOpcionesMateria.add(btnModificarMateria, "2, 4");
		btnModificarMateria.setEnabled(false);
		btnEliminarMateria = new JButton("Eliminar Materia");
		panelOpcionesMateria.add(btnEliminarMateria, "2, 6");
		btnEliminarMateria.setEnabled(false);
		JPanel panelEdicionMateriaMaster = new JPanel();
		panelAccionesMateria.setRightComponent(panelEdicionMateriaMaster);
		panelEdicionMateriaMaster.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("250px:grow"), },
				new RowSpec[] { RowSpec.decode("353px:grow"), }));
		panelEdicionMateria = new JPanel();
		panelEdicionMateriaMaster.add(panelEdicionMateria, "1, 1, fill, fill");
		panelEdicionMateria.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("4dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("60dlu"), FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		this.visibilidadPanelModificacionMateria(false);
		JLabel labelNombreNuevaMateria = new JLabel("Nombre de la Materia");
		labelNombreNuevaMateria.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionMateria.add(labelNombreNuevaMateria, "3, 4, 3, 1");
		valorNombreNuevaMateria = new JTextField();
		panelEdicionMateria.add(valorNombreNuevaMateria, "3, 6, 3, 1, fill, default");
		valorNombreNuevaMateria.setColumns(10);
		btnCancelarMateria = new JButton("Cancelar");
		btnCancelarMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarEdicionMateria();
			}
		});
		panelEdicionMateria.add(btnCancelarMateria, "3, 10");
		btnGuardarMateria = new JButton("Guardar");
		panelEdicionMateria.add(btnGuardarMateria, "5, 10");

		JScrollPane scrollPane = new JScrollPane();
		splitPaneMaterias.setRightComponent(scrollPane);

		tablaMaterias = new JTable();
		scrollPane.setViewportView(tablaMaterias);
		return panelMaterias;

	}

	private JPanel crearAdministracionUsuarios() {
		JPanel panelUsuarios = new JPanel();
		panelUsuarios.setLayout(new BoxLayout(panelUsuarios, BoxLayout.X_AXIS));
		JSplitPane splitPaneUsuarios = new JSplitPane();
		panelUsuarios.add(splitPaneUsuarios);
		JSplitPane panelAccionesUsuario = new JSplitPane();
		panelAccionesUsuario.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneUsuarios.setLeftComponent(panelAccionesUsuario);
		JPanel panelOpcionesUsuario = new JPanel();
		panelAccionesUsuario.setLeftComponent(panelOpcionesUsuario);
		panelOpcionesUsuario.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("default:grow"),
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { RowSpec.decode("fill:10dlu"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, }));
		btnAdmNuevoUsuario = new JButton("Nuevo Usuario");
		btnAdmNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablaUsuarios.clearSelection();
				btnAdmDeshabilitarUsuario.setEnabled(false);
				btnAdmModificarUsuario.setEnabled(false);
				visibilidadPanelModificacionUsuario(true);
			}
		});
		panelOpcionesUsuario.add(btnAdmNuevoUsuario, "2, 3");
		btnAdmModificarUsuario = new JButton("Modificar Usuario");
		btnAdmModificarUsuario.setEnabled(false);
		panelOpcionesUsuario.add(btnAdmModificarUsuario, "2, 5");
		btnAdmDeshabilitarUsuario = new JButton("Deshabilitar Usuario");
		panelOpcionesUsuario.add(btnAdmDeshabilitarUsuario, "2, 7");
		btnAdmDeshabilitarUsuario.setEnabled(false);
		JPanel panelEdicionUsuarioMaster = new JPanel();
		panelAccionesUsuario.setRightComponent(panelEdicionUsuarioMaster);
		panelEdicionUsuarioMaster.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("250px:grow"), },
				new RowSpec[] { RowSpec.decode("353px:grow"), }));
		panelEdicionUsuario = new JPanel();
		panelEdicionUsuarioMaster.add(panelEdicionUsuario, "1, 1, fill, fill");
		panelEdicionUsuario.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("60dlu"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		this.visibilidadPanelModificacionUsuario(false);
		JLabel labelAdmNombreNuevoUsuario = new JLabel("Nombre");
		labelAdmNombreNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionUsuario.add(labelAdmNombreNuevoUsuario, "4, 4, 3, 1");
		valorAdmNombreNuevoUsuario = new JTextField();
		panelEdicionUsuario.add(valorAdmNombreNuevoUsuario, "4, 6, 3, 1, fill, default");
		valorAdmNombreNuevoUsuario.setColumns(10);
		JLabel labelAdmApellidoNuevoUsuario = new JLabel("Apellido");
		labelAdmApellidoNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionUsuario.add(labelAdmApellidoNuevoUsuario, "4, 10, 3, 1");
		valorAdmApellidoNuevoUsuario = new JTextField();
		panelEdicionUsuario.add(valorAdmApellidoNuevoUsuario, "4, 12, 3, 1, fill, default");
		valorAdmApellidoNuevoUsuario.setColumns(10);
		JLabel labelAdmEmailNuevoUsuario = new JLabel("Email");
		labelAdmEmailNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionUsuario.add(labelAdmEmailNuevoUsuario, "4, 16, 3, 1");
		valorAdmEmailNuevoUsuario = new JFormattedTextField();
		panelEdicionUsuario.add(valorAdmEmailNuevoUsuario, "4, 18, 3, 1, fill, default");
		JLabel labelAdmPasswordNuevoUsuario = new JLabel("Password");
		labelAdmPasswordNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionUsuario.add(labelAdmPasswordNuevoUsuario, "4, 22, 3, 1");
		valorAdmPasswordNuevoUsuario = new JPasswordField();
		panelEdicionUsuario.add(valorAdmPasswordNuevoUsuario, "4, 24, 3, 1, fill, default");
		JLabel labelAdmRolNuevoUsuario = new JLabel("Rol");
		labelAdmRolNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionUsuario.add(labelAdmRolNuevoUsuario, "4, 28, 3, 1");
		valorAdmRolNuevoUsuario = new JComboBox();
		panelEdicionUsuario.add(valorAdmRolNuevoUsuario, "4, 30, 3, 1, fill, default");
		valorAdmHabilitadoNuevoUsuario = new JCheckBox("Habilitado");
		valorAdmHabilitadoNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelEdicionUsuario.add(valorAdmHabilitadoNuevoUsuario, "4, 34, 3, 1");
		btnAdmCancelarNuevoUsuario = new JButton("Cancelar");
		btnAdmCancelarNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarEdicionUsuario();
			}
		});
		panelEdicionUsuario.add(btnAdmCancelarNuevoUsuario, "4, 36");
		btnAdmGuardarNuevoUsuario = new JButton("Guardar");
		panelEdicionUsuario.add(btnAdmGuardarNuevoUsuario, "6, 36");
		JScrollPane scrollPane = new JScrollPane();
		splitPaneUsuarios.setRightComponent(scrollPane);
		tablaUsuarios = new JTable();
		scrollPane.setViewportView(tablaUsuarios);
		return panelUsuarios;

	}
}
