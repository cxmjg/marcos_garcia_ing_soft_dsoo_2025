package inscripcionExamen;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List; // Asegúrate de importar List si no lo has hecho

/**
 *
 * @author Usuario
 */
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date fecha;
    private boolean habilitado;
    private Timestamp fechaCreacion;
    private Usuario docenteTitular; // Definición de la propiedad docente1
    private Usuario docenteVocal; // Definición de la propiedad docente2
    private Materia materia; // Definición de la propiedad materia
    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa; // Definición de la propiedad mesa

    public Examen(int id, Date fecha, boolean habilitado, Timestamp fechaCreacion, 
                  Usuario docenteTitular, Usuario docenteVocal, 
                  Materia materia, Mesa mesa) { // Se agrega mesa al constructor
        this.id = id;
        this.fecha = fecha;
        this.habilitado = habilitado;
        this.fechaCreacion = fechaCreacion;
        this.docenteTitular = docenteTitular; // Inicialización de la propiedad docente1
        this.docenteVocal = docenteVocal; // Inicialización de la propiedad docente2
        this.materia = materia; // Inicialización de la propiedad materia
        this.mesa = mesa; // Inicialización de la propiedad mesa
    }

    public Examen() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getDocenteTitular() { // Método getter para docente1
        return docenteTitular;
    }

    public void setDocenteTitular(Usuario docenteTitular) { // Método setter para docente1
        this.docenteTitular = docenteTitular;
    }

    public Usuario getDocenteVocal() { // Método getter para docente2
        return docenteVocal;
    }

    public void setDocenteVocal(Usuario docenteVocal) { // Método setter para docente2
        this.docenteVocal = docenteVocal;
    }

    public Materia getMateria() { // Método getter para materia
        return materia;
    }

    public void setMateria(Materia materia) { // Método setter para materia
        this.materia = materia;
    }

    public Mesa getMesa() { // Método getter para mesa
        return mesa;
    }

    public void setMesa(Mesa mesa) { // Método setter para mesa
        this.mesa = mesa;
    }
}
