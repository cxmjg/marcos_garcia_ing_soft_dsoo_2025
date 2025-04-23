package inscripcionExamen;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class GUIAlumno {

	// Docente
	private JPanel panelAlumno;

	private JPanel materias;
	private JTable tablaMateriasAlumno;
	private JPanel panelOpcionesMateriaAlumno;
	private JButton btnInscripcionMateriaAlumno;
	private JButton btnAnularInscripcionMateriaAlumno;

	private JPanel examenes;
	private JTable tablaExamenesAlumno;
	private JPanel panelOpcionesExamenAlumno;
	private JButton btnInscripcionExamenAlumno;
	private JButton btnAnularInscripcionExamenAlumno;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAlumno window = new GUIAlumno();
					window.getPanel().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIAlumno() {
		panelAlumno = new JPanel();
		panelAlumno.setMinimumSize(new Dimension(800, 600));
		panelAlumno.setLayout(new BoxLayout(panelAlumno, BoxLayout.X_AXIS));

		JTabbedPane tabbedPaneAlumno = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneAlumno.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelAlumno.add(tabbedPaneAlumno);

		materias = crearTabMaterias();
		examenes = crearTabExamenes();

		tabbedPaneAlumno.addTab("MATERIAS", null, materias, null);
		tabbedPaneAlumno.addTab("EXAMENES", null, examenes, null);

	}

	private JPanel crearTabMaterias() {
		JPanel materias = new JPanel();
		materias.setLayout(new BoxLayout(materias, BoxLayout.X_AXIS));
		panelOpcionesMateriaAlumno = new JPanel();
		panelOpcionesMateriaAlumno.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("20dlu"), ColumnSpec.decode("80dlu"),
						ColumnSpec.decode("20dlu"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		btnInscripcionMateriaAlumno = new JButton("Inscribirme");
		btnAnularInscripcionMateriaAlumno = new JButton("Anular Inscripcion");
		panelOpcionesMateriaAlumno.add(btnInscripcionMateriaAlumno, "2, 6");
		panelOpcionesMateriaAlumno.add(btnAnularInscripcionMateriaAlumno, "2, 10");
		tablaMateriasAlumno = new JTable();
		JScrollPane scrollPaneMateriasAlumno = new JScrollPane();
		scrollPaneMateriasAlumno.setViewportView(tablaMateriasAlumno);
		JSplitPane splitPaneMaterias = new JSplitPane();
		materias.add(splitPaneMaterias);
		splitPaneMaterias.setRightComponent(scrollPaneMateriasAlumno);
		splitPaneMaterias.setLeftComponent(panelOpcionesMateriaAlumno);

		btnInscripcionMateriaAlumno.setEnabled(false);
		btnAnularInscripcionMateriaAlumno.setEnabled(false);

		return materias;
	}

	private JPanel crearTabExamenes() {
		JPanel examenes = new JPanel();
		examenes.setLayout(new BoxLayout(examenes, BoxLayout.X_AXIS));
		panelOpcionesExamenAlumno = new JPanel();
		panelOpcionesExamenAlumno.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("20dlu"), ColumnSpec.decode("80dlu"),
						ColumnSpec.decode("20dlu"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		btnInscripcionExamenAlumno = new JButton("Inscribirme");
		btnAnularInscripcionExamenAlumno = new JButton("Anular Inscripcion");
		panelOpcionesExamenAlumno.add(btnInscripcionExamenAlumno, "2, 6");
		panelOpcionesExamenAlumno.add(btnAnularInscripcionExamenAlumno, "2, 10");
		tablaExamenesAlumno = new JTable();
		JScrollPane scrollPaneExamenesAlumno = new JScrollPane();
		scrollPaneExamenesAlumno.setViewportView(tablaExamenesAlumno);
		JSplitPane splitPaneExamenes = new JSplitPane();
		examenes.add(splitPaneExamenes);
		splitPaneExamenes.setRightComponent(scrollPaneExamenesAlumno);
		splitPaneExamenes.setLeftComponent(panelOpcionesExamenAlumno);

		btnInscripcionExamenAlumno.setEnabled(false);
		btnAnularInscripcionExamenAlumno.setEnabled(false);
		return examenes;
	}

	public JPanel getPanel() {
		return panelAlumno;
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(panelAlumno, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
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

	public void asignarAccionBtnInscripcionMateria(ActionListener accion) {
		this.btnInscripcionMateriaAlumno.addActionListener(accion);
	}

	public void asignarAccionBtnAnularMateria(ActionListener accion) {
		this.btnAnularInscripcionMateriaAlumno.addActionListener(accion);
	}

	public void asignarAccionBtnInscripcionExamen(ActionListener accion) {
		this.btnInscripcionExamenAlumno.addActionListener(accion);
	}

	public void asignarAccionBtnAnularExamen(ActionListener accion) {
		this.btnAnularInscripcionExamenAlumno.addActionListener(accion);
	}

	public int getFilaSeleccionadaId(JTable tabla) {
		return (int) tabla.getValueAt(tabla.getSelectedRow(), 0);
	}

	public int getMateriaId() {
		return (int) this.tablaMateriasAlumno.getValueAt(this.tablaMateriasAlumno.getSelectedRow(), 0);
	}

	public int getExamenId() {
		return (int) this.tablaExamenesAlumno.getValueAt(this.tablaExamenesAlumno.getSelectedRow(), 0);
	}

	public String getFilaSeleccionadaEstado(JTable tabla) {
		return (String) tabla.getValueAt(tabla.getSelectedRow(), 2);
	}



	public int seleccionInscripcionMateria() {
		return JOptionPane.showOptionDialog(panelAlumno, "Seleccione opcion", "Tipo de inscripcion",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // null para icono por defecto.
				new Object[] { "Regular", "Libre", "Cancelar" }, // null para YES, NO y CANCEL
				"Regular");
	}
	
	public DefaultTableModel completarCabeceraTablaExamenes() {
		String[] columnNames = { "Id", "Condicion", "Estado", "Fecha", "Inscripcion", "Mesa", "Materia", "Presidente", "Vocal",
				"Habilitado", "Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		return table_model;
	}

	public void completarTablaExamenes(List<Examen> examenes, List<InscripcionMateria> inscripciones,
			List<InscripcionExamen> inscripcionesExamenes) {
		DefaultTableModel table_model = completarCabeceraTablaExamenes();
		this.tablaExamenesAlumno.setModel(table_model);

		if (examenes != null && inscripciones != null) {
			for (Examen examen : examenes) {
				String condicion = ""; // Valor por defecto
				String estado = "No Inscripto";
				int examenId = examen.getId();
				int examenMateriaId = examen.getMateria().getId();
				// Buscar una inscripción cuya materia coincida con la del examen
				for (InscripcionMateria inscripcion : inscripciones) {
					int inscripcionId = inscripcion.getMateria().getId();
					if (examenMateriaId == inscripcionId) {
						for (InscripcionExamen inscripcionExamen : inscripcionesExamenes) {
							int inscripcionExamenId = inscripcionExamen.getExamen().getId();
							if (examenId == inscripcionExamenId) {
								estado = "Inscripto";
								if (inscripcion.isRegular()) {
									condicion = "Regular";
								} else {
									condicion = "Libre";
								}
								break; // Salir del bucle una vez que encontramos la inscripción correspondiente
							}
						}

						table_model.addRow(new Object[] { examen.getId(), condicion, estado,
								this.dateAString(examen.getFecha(), false),
								this.dateAString(examen.getMesa().getFechaInicioInscripcion(), false) + " / "
										+ this.dateAString(examen.getMesa().getFechaFinInscripcion(), false),
								this.dateAString(examen.getMesa().getFechaInicio(), false) + " / "
										+ this.dateAString(examen.getMesa().getFechaFin(), false),
								examen.getMateria().getNombre(),
								examen.getDocenteTitular().getNombre() + " " + examen.getDocenteTitular().getApellido(),
								examen.getDocenteVocal().getNombre() + " " + examen.getDocenteVocal().getApellido(),
								examen.isHabilitado(), this.dateAString(examen.getFechaCreacion(), true) });

						break; // Salir del bucle una vez que encontramos la inscripción correspondiente
					}

				}

			}

		}
		this.tablaExamenesAlumno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tablaExamenesAlumno.getSelectedRow() > -1) {
					if (getFilaSeleccionadaEstado(tablaExamenesAlumno).equals("Inscripto")) {
						btnInscripcionExamenAlumno.setEnabled(false);
						btnAnularInscripcionExamenAlumno.setEnabled(true);
					} else {
						btnInscripcionExamenAlumno.setEnabled(true);
						btnAnularInscripcionExamenAlumno.setEnabled(false);
					}
				} else {
					btnInscripcionExamenAlumno.setEnabled(false);
					btnAnularInscripcionExamenAlumno.setEnabled(false);
				}

			}
		});
	}

	public DefaultTableModel completarCabeceraTablaMaterias() {
		String[] columnNames = { "Id", "Condicion", "Estado", "Nombre", "Fecha de Creacion" };
		DefaultTableModel table_model = new DefaultTableModel(columnNames, 0);
		return table_model;
	}

	public void completarTablaMaterias(List<Materia> materias, List<InscripcionMateria> inscripciones) {
		DefaultTableModel table_model = completarCabeceraTablaMaterias();
		this.tablaMateriasAlumno.setModel(table_model);

		if (materias != null && inscripciones != null) {
			for (Materia materia : materias) {
				String condicion = ""; // Valor por defecto
				String estado = "No Inscripto";

				for (InscripcionMateria inscripcion : inscripciones) {
					int materiaId = materia.getId();
					int inscripcionId = inscripcion.getMateria().getId();
					if (materiaId == inscripcionId) {
						if (inscripcion.isRegular()) {
							condicion = "Regular";
						} else {
							condicion = "Libre";
						}

						estado = "Inscripto";
						break; // Salir del bucle una vez que encontramos la inscripción correspondiente
					} else {
						condicion = "";
					}
				}

				table_model.addRow(new Object[] { materia.getId(), condicion, estado, materia.getNombre(),
						this.dateAString(materia.getFechaCreacion(), true) });
			}

			this.tablaMateriasAlumno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					if (tablaMateriasAlumno.getSelectedRow() > -1) {
						if (getFilaSeleccionadaEstado(tablaMateriasAlumno).equals("Inscripto")) {
							btnInscripcionMateriaAlumno.setEnabled(false);
							btnAnularInscripcionMateriaAlumno.setEnabled(true);
						} else {
							btnInscripcionMateriaAlumno.setEnabled(true);
							btnAnularInscripcionMateriaAlumno.setEnabled(false);
						}
					} else {
						btnInscripcionMateriaAlumno.setEnabled(false);
						btnAnularInscripcionMateriaAlumno.setEnabled(false);
					}

				}
			});
		}
	}

}