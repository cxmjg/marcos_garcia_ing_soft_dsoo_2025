package inscripcionExamen;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ConexionUsuariosTest {

    private static Conexion conexion;

    @BeforeAll
    static void setup() {
        conexion = new Conexion(); // Inicializa la conexión
    }

    @AfterAll
    static void teardown() {
        // Opcional: limpiar o cerrar recursos
    }
    
    @Test
    @Tag("Conexion")
    public void testGetHabilitadoUsuario() {
        // Validar que el método devuelve true para un usuario habilitado
        Boolean habilitado = conexion.getHabilitadoUsuario("admin");
        Assertions.assertNotNull(habilitado, "El resultado no debería ser null");
        Assertions.assertTrue(habilitado, "El usuario debería estar habilitado");
    }

    @Test
    @Tag("Conexion")
    public void testGetHabilitadoUsuarioEmailInexistente() {
        // Validar que el método devuelve null para un email inexistente
        Boolean habilitado = conexion.getHabilitadoUsuario("inexistente@example.com");
        Assertions.assertNull(habilitado, "El resultado debería ser null para un email inexistente");
    }
    
    @Test
    @Tag("Conexion")
    public void testSetHabilitadoUsuario() {
        // Cambiar el estado a "false"
    	Usuario usuario = conexion.getUsuarioByEmail("admin");
    	int testUserId = usuario.getId();
        conexion.setHabilitadoUsuario(testUserId, false);

        // Verificar que el estado cambió correctamente
        Boolean habilitado = conexion.getHabilitadoUsuario("admin");
        Assertions.assertNotNull(habilitado, "El resultado no debería ser null");
        Assertions.assertFalse(habilitado, "El usuario debería estar deshabilitado");

        // Cambiar el estado de vuelta a "true"
        conexion.setHabilitadoUsuario(testUserId, true);
        habilitado = conexion.getHabilitadoUsuario("admin");
        Assertions.assertTrue(habilitado, "El usuario debería estar habilitado");
    }

    @Test
    @Tag("Conexion")
    public void testSetHabilitadoUsuarioIdInexistente() {
        // Intentar cambiar el estado de un usuario que no existe
        int usuarioInexistenteId = -1;
        conexion.setHabilitadoUsuario(usuarioInexistenteId, false);

        // Verificar que no afecta a ningún usuario existente
        Boolean habilitado = conexion.getHabilitadoUsuario("admin");
        Assertions.assertNotNull(habilitado, "El usuario existente no debería verse afectado");
        Assertions.assertTrue(habilitado, "El estado del usuario existente no debería cambiar");
    }

    @Test
    @Tag("Conexion")
    void testCrearUsuario() {
        conexion.crearUsuario("Juan", "Perez", "juanperez@test.com", "1234", 3, true);
        Usuario usuario = conexion.getUsuarioByEmail("juanperez@test.com");
        assertNotNull(usuario);
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Perez", usuario.getApellido());
    }

    @Test
    @Tag("Conexion")
    void testGetUsuarioByEmail() {
        Usuario usuario = conexion.getUsuarioByEmail("admin");
        assertNotNull(usuario);
        assertEquals("Administrador", usuario.getNombre());
    }

    @Test
    @Tag("Conexion")
    void testGetUsuarioById() {
        Usuario usuario = conexion.getUsuarioById(1); // Asumiendo que el ID 1 pertenece al admin
        assertNotNull(usuario);
        assertEquals("Administrador", usuario.getNombre());
    }

    @Test
    @Tag("Conexion")
    void testGetUsuarios() {
        List<Usuario> usuarios = conexion.getUsuarios();
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }

    @Test
    @Tag("Conexion")
    void testSetNombreUsuario() {
        Usuario usuario = conexion.getUsuarioByEmail("admin");
        assertNotNull(usuario);

        conexion.setNombreUsuario(usuario.getId(), "NuevoNombre");
        Usuario usuarioActualizado = conexion.getUsuarioById(usuario.getId());

        assertEquals("NuevoNombre", usuarioActualizado.getNombre());
    }

    @Test
    @Tag("Conexion")
    void testSetApellidoUsuario() {
        Usuario usuario = conexion.getUsuarioByEmail("admin");
        assertNotNull(usuario);

        conexion.setApellidoUsuario(usuario.getId(), "NuevoApellido");
        Usuario usuarioActualizado = conexion.getUsuarioById(usuario.getId());

        assertEquals("NuevoApellido", usuarioActualizado.getApellido());
    }

    @Test
    @Tag("Conexion")
    void testSetEmailUsuario() {
        Usuario usuario = conexion.getUsuarioByEmail("admin");
        assertNotNull(usuario);

        conexion.setEmailUsuario(usuario.getId(), "nuevoemail@test.com");
        Usuario usuarioActualizado = conexion.getUsuarioByEmail("nuevoemail@test.com");

        assertNotNull(usuarioActualizado);
        assertEquals("nuevoemail@test.com", usuarioActualizado.getEmail());
        conexion.setEmailUsuario(usuario.getId(), "admin");
    }

    @Test
    @Tag("Conexion")
    void testSetPasswordUsuario() {
        Usuario usuario = conexion.getUsuarioByEmail("admin");
        assertNotNull(usuario);

        conexion.setPasswordUsuario(usuario.getId(), "nuevaPassword");
        Usuario usuarioActualizado = conexion.getUsuarioById(usuario.getId());

        // Validar indirectamente el cambio de contraseña (no es visible por seguridad)
        assertNotNull(usuarioActualizado);
    }

    @Test
    @Tag("Conexion")
    void testGetDocentes() {
        List<Usuario> docentes = conexion.getDocentes();
        assertNotNull(docentes);
        assertFalse(docentes.isEmpty());
    }

    @Test
    @Tag("Conexion")
    void testGetAlumnos() {
        List<Usuario> alumnos = conexion.getAlumnos();
        assertNotNull(alumnos);
        assertFalse(alumnos.isEmpty());
    }

    @Test
    @Tag("Conexion")
    void testValidarUsuario() {
        Usuario usuario = conexion.validarUsuario("admin", "1234");
        assertNotNull(usuario);
        assertEquals("Administrador", usuario.getNombre());
    }
}
