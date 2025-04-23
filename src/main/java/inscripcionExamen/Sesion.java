package inscripcionExamen;
import javax.swing.JPanel;

import java.awt.Component;

public interface Sesion {
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public Component getPanel();
	public InscripcionExamen getInscripcionExamen();
	public void setInscripcionExamen(InscripcionExamen inscripcion);
	public InscripcionMateria getInscripcionMateria();
	public void setConexion(Conexion base);
}
