
package inscripcionExamen; 

public class Main {

	public static void main(String[] args) {
		Conexion base = new Conexion();
		GUI interfaz = new GUI();
		Funcion funciones = new Funcion(interfaz, base);
		
		//Datos de pruebas
		base.crearPrueba();
		//Pantalla login
		interfaz.asignarAccionBtnLogin(funciones.login());
		interfaz.asignarAccionBtnGuardarNuevoUsuario(funciones.crearRegistro());
	}
}