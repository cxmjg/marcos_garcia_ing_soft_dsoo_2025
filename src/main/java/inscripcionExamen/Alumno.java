package inscripcionExamen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Alumno implements Sesion{
	private Usuario usuario;
	private Conexion base;
	private GUIAlumno interfaz;
	
	public Alumno() {
		interfaz = new GUIAlumno();
		interfaz.asignarAccionBtnInscripcionMateria(inscribirMateria());
		interfaz.asignarAccionBtnAnularMateria(anularMateria());
		interfaz.asignarAccionBtnInscripcionExamen(this.inscribirExamen());
		interfaz.asignarAccionBtnAnularExamen(this.anularExamen());
		
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
		interfaz.completarTablaExamenes(base.getExamenes(), base.getInscripcionesMateriasByAlumno(usuario), base.getInscripcionesExamenesByAlumno(usuario));
		interfaz.completarTablaMaterias(base.getMaterias(), base.getInscripcionesMateriasByAlumno(usuario));
		
	}
	
	public Boolean puedeInscribir(Date cierreInscripcion) {
	    Date hoy = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    return cierreInscripcion.after(hoy);
	}
	
	
	public ActionListener inscribirMateria() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cursada = interfaz.seleccionInscripcionMateria();
				Boolean regular = true;
				if ( cursada == 0 || cursada == 1) {
					if (cursada == 1) {
						regular = false;
					}
					base.crearInscripcionMateria(usuario, base.getMateriaById(interfaz.getMateriaId()), regular);
					interfaz.completarTablaMaterias(base.getMateriasHabilitadas(), base.getInscripcionesMateriasByAlumno(usuario));
					interfaz.completarTablaExamenes(base.getExamenesHabilitados(), base.getInscripcionesMateriasByAlumno(usuario), base.getInscripcionesExamenesByAlumno(usuario));
				}
				
			}
		};
	}
	
	public ActionListener anularMateria() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Materia materia = base.getMateriaById(interfaz.getMateriaId());
				List<InscripcionExamen> inscripciones = base.getInscripcionesExamenesByAlumnoMateria(usuario, materia);
				base.eliminarInscripcionMateria(usuario, materia);
				for (InscripcionExamen inscripcion : inscripciones) {
					Examen examen = inscripcion.getExamen();
					base.eliminarInscripcionExamen(usuario, examen);
				}
				interfaz.completarTablaMaterias(base.getMateriasHabilitadas(), base.getInscripcionesMateriasByAlumno(usuario));
				interfaz.completarTablaExamenes(base.getExamenesHabilitados(), base.getInscripcionesMateriasByAlumno(usuario), base.getInscripcionesExamenesByAlumno(usuario));
			}
		};
	}
	
	public ActionListener inscribirExamen() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Examen examen = base.getExamenById(interfaz.getExamenId());
				Mesa mesa = examen.getMesa();
				if (puedeInscribir(mesa.getFechaFinInscripcion()) && !puedeInscribir(mesa.getFechaInicioInscripcion())) {
					Boolean regular = false;
					List<InscripcionMateria> inscripcionesMateria = base.getInscripcionesMateriasByAlumno(usuario);
					for (InscripcionMateria inscripcion : inscripcionesMateria) {
						int insMateria = inscripcion.getMateria().getId();
						int materia = examen.getMateria().getId();
						if (insMateria == materia) {
							regular = inscripcion.isRegular();
							break;
						}
					}
					base.crearInscripcionExamen(usuario, examen, regular);
					interfaz.completarTablaExamenes(base.getExamenes(), base.getInscripcionesMateriasByAlumno(usuario), base.getInscripcionesExamenesByAlumno(usuario));
				} else if (!puedeInscribir(mesa.getFechaFinInscripcion())) {
					interfaz.mostrarMensaje("Ya paso el periodo de inscripcion");
				} else {
					interfaz.mostrarMensaje("Todavia no abrio el periodo de inscripcion");
				}
				
			}
		};
	}
	
	public ActionListener anularExamen() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Examen examen = base.getExamenById(interfaz.getExamenId());
				base.eliminarInscripcionExamen(usuario, examen);
				interfaz.completarTablaExamenes(base.getExamenes(), base.getInscripcionesMateriasByAlumno(usuario), base.getInscripcionesExamenesByAlumno(usuario));
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
