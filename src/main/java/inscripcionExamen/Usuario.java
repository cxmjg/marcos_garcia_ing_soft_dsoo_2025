package inscripcionExamen;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = true)
    private Rol rol;

    private boolean habilitado;
    private Timestamp fechaCreacion;

    public Usuario() {}

    public Usuario(int id, String contrasena, String nombre, String apellido, String email,
                   Rol rol, boolean habilitado, Timestamp fechaCreacion) {
        this.id = id;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.habilitado = habilitado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

}
