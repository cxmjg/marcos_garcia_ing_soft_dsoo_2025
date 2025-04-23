package inscripcionExamen;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 *
 * @author Usuario
 */
public class InscripcionExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp fechaCreacion;
    private boolean libre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Usuario alumno; // Propiedad alumno

    @ManyToOne // Indica una relación Many-to-One con la clase Examen
    private Examen examen; // Definición de la propiedad examen

    public InscripcionExamen(int id, Timestamp fechaCreacion, boolean libre, Usuario alumno, Examen examen) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.libre = libre;
        this.alumno = alumno; // Inicialización de la propiedad alumno
        this.examen = examen; // Inicialización de la propiedad examen
    }

    public InscripcionExamen() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public Usuario getAlumno() { // Método getter para alumno
        return alumno;
    }

    public void setAlumno(Usuario alumno) { // Método setter para alumno
        this.alumno = alumno;
    }

    public Examen getExamen() { // Método getter para examen
        return examen;
    }

    public void setExamen(Examen examen) { // Método setter para examen
        this.examen = examen;
    }
}
