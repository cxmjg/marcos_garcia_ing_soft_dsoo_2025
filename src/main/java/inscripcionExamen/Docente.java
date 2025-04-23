package inscripcionExamen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Docente implements Sesion {
	private Usuario usuario;
	private Conexion base;
	private GUIDocente interfaz;

	public Docente() {
		interfaz = new GUIDocente();
		interfaz.asignarAccionBtnBuscarExamenDocente(buscarDocenteExamenes());
		interfaz.asignarAccionBtnVerAsistencia(verAsistenciaDocenteExamenes());
		interfaz.asignarAccionBtnExportarExamenes(exportarTablaExamenes());
		interfaz.asignarAccionBtnExportarAsistencia(exportarTablaAsistencias());
		
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
		interfaz.completarTablaDocenteExamenes(interfaz.getTablaTodoDocente(),
				base.getExamenesByDocente(usuario));
		interfaz.completarTablaDocenteExamenes(interfaz.getTablaPresidenteDocente(),
				base.getExamenesByDocentePresidente(usuario));
		interfaz.completarTablaDocenteExamenes(interfaz.getTablaVocalDocente(),
				base.getExamenesByDocenteVocal(usuario));

	}

	public ActionListener buscarDocenteExamenes() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaz.getTablaAsistencia().setVisible(false);
				interfaz.setHabilitacionExportarAsistencias(false);
				if (interfaz.getRadioDocenteTodos()) {
					interfaz.setTablaDocente("tablaTodoExamenesDocente");
				}
				if (interfaz.getRadioDocentePresidente()) {
					interfaz.setTablaDocente("tablaPresidenteExamenesDocente");
				}

				if (interfaz.getRadioDocenteVocal()) {
					interfaz.setTablaDocente("tablaVocalExamenesDocente");
				}

			}
		};
	}
	
	// Exportar tabla de examenes
	
	public ActionListener exportarTablaExamenes() {
	    return new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                // Determinar los exámenes según el rol
	                List<Examen> examenes;
	                if (interfaz.getRadioDocentePresidente()) {
	                    examenes = base.getExamenesByDocentePresidente(usuario);
	                } else if (interfaz.getRadioDocenteVocal()) {
	                    examenes = base.getExamenesByDocenteVocal(usuario);
	                } else {
	                    examenes = base.getExamenesByDocente(usuario);
	                }

	                // Seleccionar ubicación del archivo
	                JFileChooser selectorDeArchivos = new JFileChooser();
	                selectorDeArchivos.setDialogTitle("Guardar archivo CSV");
	                selectorDeArchivos.setSelectedFile(new java.io.File("examenes.csv"));

	                int seleccionDeUsuario = selectorDeArchivos.showSaveDialog(null);

	                if (seleccionDeUsuario == JFileChooser.APPROVE_OPTION) {
	                    String rutaDeArchivo = selectorDeArchivos.getSelectedFile().getAbsolutePath();

	                    // Crear archivo CSV
	                    try (FileWriter writer = new FileWriter(rutaDeArchivo)) {
	                        // Escribir encabezados
	                        writer.write("Id,Fecha,Mesa,Materia,Docente Titular,Docente Vocal,Habilitado,Fecha de Creacion\n");

	                        // Escribir datos
	                        for (Examen examen : examenes) {
	                            writer.write(examen.getId() + "," + examen.getFecha() + "," + examen.getMesa() + "," + examen.getMateria() + "," + examen.getDocenteTitular() + "," + examen.getDocenteVocal() + "," + examen.isHabilitado() + "," + examen.getFechaCreacion() + "\n");
	                        }
	                    }

	                    // Confirmación
	                    JOptionPane.showMessageDialog(null, "Archivo guardado en: " + rutaDeArchivo, "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
	                } else {
	                    JOptionPane.showMessageDialog(null, "La exportación fue cancelada.", "Cancelación", JOptionPane.WARNING_MESSAGE);
	                }
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(null, "Error al exportar el archivo CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    };
	}
	
	// Exportar tabla de asistencias
	
	public ActionListener exportarTablaAsistencias() {
	    return new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                // Obtener las asistencias según los datos actuales
	            	int idExamen = interfaz.getExamenSeleccionadoId();
					List<InscripcionExamen> inscripciones = base.getInscripcionesExamenesByExamen(base.getExamenById(idExamen));

	                // Seleccionar ubicación del archivo
	                JFileChooser selectorDeArchivo = new JFileChooser();
	                selectorDeArchivo.setDialogTitle("Guardar archivo CSV");
	                selectorDeArchivo.setSelectedFile(new java.io.File("asistencias.csv"));

	                int seleccionDeUsuario = selectorDeArchivo.showSaveDialog(null);

	                if (seleccionDeUsuario == JFileChooser.APPROVE_OPTION) {
	                    String rutaDeArchivo = selectorDeArchivo.getSelectedFile().getAbsolutePath();

	                    // Crear archivo CSV
	                    try (FileWriter writer = new FileWriter(rutaDeArchivo)) {
	                        // Escribir encabezados
	                        writer.write("Id,Nombre,Apellido,Email,Tipo,Fecha de Creación\n");

	                        // Escribir datos
	                        for (InscripcionExamen asistencia : inscripciones) {
	                            Usuario alumno = asistencia.getAlumno();
	                            String tipo = asistencia.isLibre() ? "Libre" : "Regular";
	                            writer.write(asistencia.getId() + "," + alumno.getNombre() + "," + alumno.getApellido() + "," + alumno.getEmail() + "," +tipo + "," + dateAString(asistencia.getFechaCreacion()) + "\n");
	                        }
	                    }

	                    // Confirmación
	                    JOptionPane.showMessageDialog(null, "Archivo guardado en: " + rutaDeArchivo, "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
	                } else {
	                    JOptionPane.showMessageDialog(null, "La exportación fue cancelada.", "Cancelación", JOptionPane.WARNING_MESSAGE);
	                }
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(null, "Error al exportar el archivo CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }

	        // Método para convertir la fecha a string
	        private String dateAString(Timestamp fechaCreacion) {
	            // Formatear la fecha a un formato adecuado (por ejemplo, "dd/MM/yyyy HH:mm:ss")
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	            return sdf.format(fechaCreacion);
	        }
	    };
	}

	
	
	public ActionListener verAsistenciaDocenteExamenes() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaz.getTablaAsistencia().setVisible(true);
				int idExamen = interfaz.getExamenSeleccionadoId();
				List<InscripcionExamen> inscripciones = base.getInscripcionesExamenesByExamen(base.getExamenById(idExamen));
				interfaz.completarTablaDocenteAsistencia(inscripciones);
				interfaz.setHabilitacionExportarAsistencias(true);
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
