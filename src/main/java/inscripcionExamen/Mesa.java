package inscripcionExamen;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;



/**
 *
 * @author Usuario
 */
@Entity // Asegúrate de que la clase sea una entidad JPA
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private Set<Examen> examenes; 

	private Date fechaInicio;
    private Date fechaFin;
    private Date fechaInicioInscripcion;
    private Date fechaFinInscripcion;
    private Timestamp fechaCreacion;
    private boolean habilitado;

    public Mesa(int id, Date fechaInicio, Date fechaFin, 
                Date fechaInicioInscripcion, Date fechaFinInscripcion, 
                Timestamp fechaCreacion, boolean habilitado) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaFinInscripcion = fechaFinInscripcion;
        this.fechaCreacion = fechaCreacion;
        this.habilitado = habilitado;
    }

    public Mesa() {}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
    	return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicioInscripcion() {
    	return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(Date fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public Date getFechaFinInscripcion() {
    	return fechaFinInscripcion;
    }

    public void setFechaFinInscripcion(Date fechaFinInscripcion) {
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
    public Set<Examen> getExamenes() {
		return examenes;
	}

	public void setExamenes(Set<Examen> examenes) {
		this.examenes = examenes;
	}

}
