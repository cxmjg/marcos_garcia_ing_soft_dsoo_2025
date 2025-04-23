package inscripcionExamen;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.sql.Timestamp;
import java.util.Date;

@Entity // Asegúrate de que la clase sea una entidad JPA
public class InscripcionMateria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Usuario alumno;     // Propiedad alumno
    private Materia materia;    // Propiedad materia
    private Timestamp fechaCreacion; // Propiedad fechaCreacion
    private boolean regular;     // Propiedad regular

    public InscripcionMateria(int id, Usuario alumno, Materia materia, Timestamp fechaCreacion, Boolean regular) {
    	this.id = id;
    	this.alumno = alumno;
    	this.materia = materia;
    	this.fechaCreacion = fechaCreacion;
    	this.regular = regular;
    }
    
    public InscripcionMateria() {
    	
    }

    // Método getter para la propiedad alumno
    public Usuario getAlumno() {
        return alumno;
    }

    // Método setter para la propiedad alumno
    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    // Método getter para la propiedad materia
    public Materia getMateria() {
        return materia;
    }

    // Método setter para la propiedad materia
    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    // Método getter para la propiedad id
    public int getId() {
        return id;
    }

    // Método setter para la propiedad id
    public void setId(int id) {
        this.id = id;
    }

    // Método getter para la propiedad fechaCreacion
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    // Método setter para la propiedad fechaCreacion
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Método getter para la propiedad regular
    public boolean isRegular() {
        return regular;
    }

    // Método setter para la propiedad regular
    public void setRegular(boolean regular) {
        this.regular = regular;
    }
}
