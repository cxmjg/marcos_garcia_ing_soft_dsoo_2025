package inscripcionExamen;

public class Factoria {
	private Sesion sesion;
	
	public Sesion iniciarSesion(Usuario usuario, String rolusuario) {
		if (rolusuario.equals("Administrador")) {
			crearAdministrador(usuario);
		}
		if (rolusuario.equals("Docente")) {
			crearDocente(usuario);
		}
		if (rolusuario.equals("Alumno")) {
			crearAlumno(usuario);
		}
		
		return sesion;
		
	}
	
	private Sesion crearAdministrador(Usuario usuario) {
		sesion = new Administrador();
		sesion.setUsuario(usuario);
		return sesion;
	}
	
	private Sesion crearDocente(Usuario usuario) {
		sesion = new Docente();
		sesion.setUsuario(usuario);
		return sesion;
	}
	
	private Sesion crearAlumno(Usuario usuario) {
		sesion = new Alumno();
		sesion.setUsuario(usuario);
		return sesion;
	}
}