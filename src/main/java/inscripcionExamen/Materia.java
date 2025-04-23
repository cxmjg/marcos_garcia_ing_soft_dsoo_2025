/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inscripcionExamen;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private List<Examen> examenes; // o el tipo que corresponda
    private String nombre;
    private Timestamp fechaCreacion;
    private boolean habilitado;

    public Materia(int id, String nombre, Timestamp fechaCreacion, boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.habilitado = habilitado;
    }
    

    public Materia() {}
    
    public List<Examen> getExamenes() {
        return examenes;
    }
    
    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
