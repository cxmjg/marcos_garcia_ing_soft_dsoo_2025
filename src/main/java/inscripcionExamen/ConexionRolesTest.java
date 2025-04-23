package inscripcionExamen;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ConexionRolesTest {

    private static Conexion conexion;
    private static int testRoleId;

    @BeforeAll
    public static void setup() {
        conexion = new Conexion();
        // Crear un rol de prueba y obtener su ID
        conexion.crearRol("Test Role", "Rol para pruebas");
        Rol rol = conexion.getRolByNombre("Test Role");
        testRoleId = rol.getId();
    }

    @Test
    @Tag("Conexion")
    public void testCrearRol() {
        // Crear un nuevo rol
    	conexion.crearRol("Nuevo Rol", "Descripción del nuevo rol");
        int nuevoRolId = conexion.getRolByNombre("Nuevo Rol").getId();
        Assertions.assertTrue(nuevoRolId > 0, "El ID del rol debería ser mayor que 0");

        // Verificar que el rol fue creado correctamente
        Rol rol = conexion.getRolById(nuevoRolId);
        Assertions.assertNotNull(rol, "El rol debería existir");
        Assertions.assertEquals("Nuevo Rol", rol.getNombre(), "El nombre del rol debería coincidir");
        Assertions.assertEquals("Descripción del nuevo rol", rol.getDescripcion(), "La descripción debería coincidir");
    }

    @Test
    @Tag("Conexion")
    public void testObtenerRol() {
        // Obtener el rol creado en el setup
        Rol rol = conexion.getRolById(testRoleId);
        Assertions.assertNotNull(rol, "El rol debería existir");
        Assertions.assertEquals("Test Role", rol.getNombre(), "El nombre del rol debería coincidir");
        Assertions.assertEquals("Rol para pruebas", rol.getDescripcion(), "La descripción debería coincidir");
    }


    @Test
    @Tag("Conexion")
    public void testObtenerTodosLosRoles() {
        // Obtener todos los roles
        List<Rol> roles = conexion.getRoles();
        Assertions.assertNotNull(roles, "La lista de roles no debería ser null");
        Assertions.assertFalse(roles.isEmpty(), "La lista de roles no debería estar vacía");

        // Verificar que el rol de prueba esté en la lista
        boolean encontrado = roles.stream().anyMatch(r -> r.getId() == testRoleId);
        Assertions.assertTrue(encontrado, "El rol de prueba debería estar en la lista");
    }

    @AfterAll
    public static void cleanup() {
    }
}
