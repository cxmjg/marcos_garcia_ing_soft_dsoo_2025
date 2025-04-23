package inscripcionExamen;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Dimension;

public class GUIDocente {

	// Docente
	private JButton btnDocenteExportarAsistencia;
	private JButton btnDocenteExportarExamenes;
	private JButton btnDocenteVerAsistencia;
	private JRadioButton radioDocenteVocal;
	private JRadioButton radioDocentePresidente;
	private JRadioButton radioDocenteVerTodos;
	private JPanel panelDocente;
	private JSplitPane splitPaneDocente;
	private JSplitPane panelAlumnosDocente;
	private JButton btnBuscarExamenDocente;
	private JTable tablaTodoExamenesDocente;
	private JTable tablaPresidenteExamenesDocente;
	private JTable tablaVocalExamenesDocente;
	private JPanel panelTablasExamenesDocente;
	private JTable tablaAsistenciaDocente;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDocente window = new GUIDocente();
					window.getPanel().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIDocente() {
		// Panel Docente
		panelDocente = new JPanel();
		panelDocente.setMinimumSize(new Dimension(800, 600));
		panelDocente.setLayout(new BoxLayout(panelDocente, BoxLayout.X_AXIS));
		splitPaneDocente = new JSplitPane();
		splitPaneDocente.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelDocente.add(splitPaneDocente);

		// Tabla de Examenes
		JSplitPane panelExamenesDocente = this.crearExamenesDocente();
		splitPaneDocente.setLeftComponent(panelExamenesDocente);

		// Tabla de Asistencia
		JSplitPane panelAlumnosDocente = this.crearAsistenciaDocente();
		splitPaneDocente.setRightComponent(panelAlumnosDocente);
	}

	public JPanel getPanel() {
		return panelDocente;
	}

	private JSplitPane crearAsistenciaDocente() {
		JSplitPane panelAlumnosDocente = new JSplitPane();
		panelAlumnosDocente.setDividerSize(3);
		panelAlumnosDocente.setPreferredSize(new Dimension(179, 200));
		panelAlumnosDocente.setSize(new Dimension(0, 200));
		panelAlumnosDocente.setMaximumSize(new Dimension(2147483647, 200));
		JScrollPane scrollPaneAlumnosDocente = new JScrollPane();
		panelAlumnosDocente.setRightComponent(scrollPaneAlumnosDocente);
		tablaAsistenciaDocente = new JTable();
		scrollPaneAlumnosDocente.setViewportView(tablaAsistenciaDocente);
		JPanel opcionesAlumnosDocente = new JPanel();
		panelAlumnosDocente.setLeftComponent(opcionesAlumnosDocente);
		opcionesAlumnosDocente.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("20dlu"), ColumnSpec.decode("20dlu:grow"),
						ColumnSpec.decode("20dlu:grow"), ColumnSpec.decode("20dlu"), },
				new RowSpec[] { RowSpec.decode("fill:50dlu"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("fill:default"), RowSpec.decode("fill:default"), }));
		JLabel labelAsistencia = new JLabel("ASISTENCIA");
		labelAsistencia.setHorizontalAlignment(SwingConstants.CENTER);
		opcionesAlumnosDocente.add(labelAsistencia, "2, 1, 2, 1");
		btnDocenteExportarAsistencia = new JButton("Exportar Tabla");
		btnDocenteExportarAsistencia.setEnabled(false);
		opcionesAlumnosDocente.add(btnDocenteExportarAsistencia, "2, 3, 2, 1");
		return panelAlumnosDocente;
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

	private JSplitPane crearExamenesDocente() {
		JSplitPane panelExamenesDocente = new JSplitPane();
		JPanel opcionesExamenesDocente = new JPanel();
		panelExamenesDocente.setLeftComponent(opcionesExamenesDocente);
		opcionesExamenesDocente.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("20dlu"), ColumnSpec.decode("20dlu:grow"),
						ColumnSpec.decode("20dlu:grow"), ColumnSpec.decode("20dlu"), },
				new RowSpec[] { RowSpec.decode("fill:50dlu"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("fill:default:grow"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("fill:default:grow"), }));
		JLabel labelExamenes = new JLabel("EXAMENES");
		labelExamenes.setHorizontalAlignment(SwingConstants.CENTER);
		opcionesExamenesDocente.add(labelExamenes, "2, 1, 2, 1");
		ButtonGroup G1 = new ButtonGroup();
		radioDocenteVerTodos = new JRadioButton("Ver todos");
		radioDocentePresidente = new JRadioButton("Soy Presidente");
		radioDocenteVocal = new JRadioButton("Soy Vocal");
		opcionesExamenesDocente.add(radioDocenteVerTodos, "2, 3, 2, 1");
		opcionesExamenesDocente.add(radioDocentePresidente, "2, 5, 2, 1");
		opcionesExamenesDocente.add(radioDocenteVocal, "2, 7, 2, 1");
		G1.add(radioDocenteVerTodos);
		G1.add(radioDocentePresidente);
		G1.add(radioDocenteVocal);

		btnDocenteVerAsistencia = new JButton("Ver Asistencia");
		btnDocenteExportarExamenes = new JButton("Exportar Tabla");

		btnBuscarExamenDocente = new JButton("Buscar");
		opcionesExamenesDocente.add(btnBuscarExamenDocente, "2, 9, 2, 1");
		opcionesExamenesDocente.add(btnDocenteVerAsistencia, "2, 12, 2, 1");
		opcionesExamenesDocente.add(btnDocenteExportarExamenes, "2, 14, 2, 1");

		panelTablasExamenesDocente = new JPanel();
		panelExamenesDocente.setRightComponent(panelTablasExamenesDocente);
		panelTablasExamenesDocente.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPaneTablaTodoExamenesDocente = new JScrollPane();
		panelTablasExamenesDocente.add(scrollPaneTablaTodoExamenesDocente, "tablaTodoExamenesDocente");

		tablaTodoExamenesDocente = new JTable();
		scrollPaneTablaTodoExamenesDocente.setViewportView(tablaTodoExamenesDocente);

		JScrollPane scrollPaneTablaPresidenteExamenesDocente = new JScrollPane();
		panelTablasExamenesDocente.add(scrollPaneTablaPresidenteExamenesDocente, "tablaPresidenteExamenesDocente");

		tablaPresidenteExamenesDocente = new JTable();
		scrollPaneTablaPresidenteExamenesDocente.setViewportView(tablaPresidenteExamenesDocente);

		JScrollPane scrollPaneTablaVocalExamenesDocente = new JScrollPane();
		panelTablasExamenesDocente.add(scrollPaneTablaVocalExamenesDocente, "tablaVocalExamenesDocente");

		tablaVocalExamenesDocente = new JTable();
		scrollPaneTablaVocalExamenesDocente.setViewportView(tablaVocalExamenesDocente);
		return panelExamenesDocente;
	}

	// Metodos Docente Examenes

	public void asignarAccionBtnVerAsistencia(ActionListener accion) {
		this.btnDocenteVerAsistencia.addActionListener(accion);
	}

	public void asignarAccionBtnExportarExamenes(ActionListener accion) {
		this.btnDocenteExportarExamenes.addActionListener(accion);
	}

	public void asignarAccionBtnExportarAsistencia(ActionListener accion) {
		this.btnDocenteExportarAsistencia.addActionListener(accion);
	}
	
	public void setHabilitacionExportarAsistencias(Boolean habilitado) {
		btnDocenteExportarAsistencia.setEnabled(habilitado);
	}

	public void asignarAccionBtnBuscarExamenDocente(ActionListener accion) {
		this.btnBuscarExamenDocente.addActionListener(accion);
	}

	public Boolean getRadioDocenteTodos() {
		return this.radioDocenteVerTodos.isSelected();
	}

	public Boolean getRadioDocentePresidente() {
		return this.radioDocentePresidente.isSelected();
	}

	public Boolean getRadioDocenteVocal() {
		return this.radioDocenteVocal.isSelected();
	}

	public JTable getTablaTodoDocente() {
		return this.tablaTodoExamenesDocente;
	}

	public JTable getTablaPresidenteDocente() {
		return this.tablaPresidenteExamenesDocente;
	}

	public JTable getTablaVocalDocente() {
		return this.tablaVocalExamenesDocente;
	}
	
	public JTable getTablaAsistencia() {
		return tablaAsistenciaDocente;
	}

	public void setTablaDocente(String tabla) {
		this.tablaTodoExamenesDocente.clearSelection();
		this.tablaPresidenteExamenesDocente.clearSelection();
		this.tablaVocalExamenesDocente.clearSelection();
		((CardLayout) panelTablasExamenesDocente.getLayout()).show(panelTablasExamenesDocente, tabla);
	}

	public int getExamenSeleccionadoId() {
		int row = this.tablaTodoExamenesDocente.getSelectedRow();
		if (row != -1) {
			return (int) this.tablaTodoExamenesDocente.getValueAt(this.tablaTodoExamenesDocente.getSelectedRow(), 0);
		} else {
			row = this.tablaPresidenteExamenesDocente.getSelectedRow();
			if (row != -1) {
				return (int) this.tablaPresidenteExamenesDocente
						.getValueAt(this.tablaPresidenteExamenesDocente.getSelectedRow(), 0);
			} else {
				row = this.tablaVocalExamenesDocente.getSelectedRow();
				if (row != -1) {
					return (int) this.tablaVocalExamenesDocente
							.getValueAt(this.tablaVocalExamenesDocente.getSelectedRow(), 0);
				}

			}
		}
		return row;
	}

	public DefaultTableModel completarCabeceraTablaDocenteExamenes() {
		String[] columnNames = { "Id", "Fecha", "Mesa", "Materia", "Presidente", "Vocal", "Habilitado",
				"Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		return table_model;
	}

	public void completarTablaDocenteExamenes(JTable tabla, List<Examen> examenes) {
		DefaultTableModel table_model = completarCabeceraTablaDocenteExamenes();
		tabla.setModel(table_model);
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

			tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					// Acciones al seleccionar una fila
				}
			});
		}

	}

	public DefaultTableModel completarCabeceraTablaDocenteAsistencia() {
		String[] columnNames = { "Id", "Nombre", "Apellido", "Email", "Tipo", "Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		this.tablaAsistenciaDocente.setModel(table_model);
		return table_model;
	}

	public void completarTablaDocenteAsistencia(List<InscripcionExamen> inscripciones) {
		DefaultTableModel table_model = completarCabeceraTablaDocenteAsistencia();
		for (InscripcionExamen inscripcion : inscripciones) {
			Usuario alumno = inscripcion.getAlumno();
			String tipo = "Regular";
			if (inscripcion.isLibre()) {
				tipo = "Libre";
			}
			table_model.addRow(
					new Object[] { inscripcion.getId(), alumno.getNombre(), alumno.getApellido(), alumno.getEmail(),
							tipo, this.dateAString(inscripcion.getFechaCreacion(), true) });
		}

		this.tablaAsistenciaDocente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// Acciones al seleccionar una fila
			}
		});
	}
	


}
