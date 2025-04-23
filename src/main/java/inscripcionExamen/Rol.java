package inscripcionExamen;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private Set<Usuario> usuarios; 

    public Rol() {}

    public Rol(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Método getter para usuarios
    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    // Método setter para usuarios
    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // Getters y setters para los otros atributos
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
