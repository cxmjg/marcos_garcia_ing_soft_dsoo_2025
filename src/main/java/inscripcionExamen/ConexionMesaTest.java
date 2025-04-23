package inscripcionExamen;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConexionMesaTest {

	private static Conexion conexion;
    
    @BeforeAll
    public static void setup() {
        conexion = new Conexion();
        conexion.crearPrueba(); // Poblar la base de datos con datos iniciales
        
        
    }

    @Test
    @Tag("Conexion")
    void testGetMesaById() {
        Mesa mesa = conexion.getMesaById(1);
        assertNotNull(mesa, "La mesa no debería ser nula");
        assertEquals(1, mesa.getId(), "El ID de la mesa debería ser 1");
    }

    @Test
    @Tag("Conexion")
    void testGetMesas() {
        List<Mesa> mesas = conexion.getMesas();
        assertNotNull(mesas, "La lista de mesas no debería ser nula");
        assertFalse(mesas.isEmpty(), "La lista de mesas no debería estar vacía");
    }

    @Test
    @Tag("Conexion")
    void testGetMesasHabilitadas() {
        List<Mesa> mesasHabilitadas = conexion.getMesasHabilitadas();
        assertNotNull(mesasHabilitadas, "La lista de mesas habilitadas no debería ser nula");
        assertTrue(mesasHabilitadas.stream().allMatch(Mesa::getHabilitado), "Todas las mesas deberían estar habilitadas");
    }

    @Test
    @Tag("Conexion")
    void testCrearMesa() {
        conexion.crearMesa("21-10-2024", "22-10-2024", "15-10-2024", "20-10-2024");
        List<Mesa> mesas = conexion.getMesas();
        
        assertTrue(
            mesas.stream().anyMatch(m -> m.getFechaInicio().toString().contains("2024-10-21")),
            "La nueva mesa debería haberse creado correctamente"
        );
    }

    @Test
    @Tag("Conexion")
    void testSetFechaInicioMesa() {
        conexion.setFechaInicioMesa(1, "25-10-2024");
        Mesa mesa = conexion.getMesaById(1);
        assertEquals("25-10-2024", new SimpleDateFormat("dd-MM-yyyy").format(mesa.getFechaInicio()), "La fecha de inicio de la mesa debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetFechaFinMesa() {
        conexion.setFechaFinMesa(1, "30-10-2024");
        Mesa mesa = conexion.getMesaById(1);
        assertEquals("30-10-2024", new SimpleDateFormat("dd-MM-yyyy").format(mesa.getFechaFin()), "La fecha de fin de la mesa debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetFechaInicioInscripcionMesa() {
        conexion.setFechaInicioInscripcionMesa(1, "10-10-2024");
        Mesa mesa = conexion.getMesaById(1);
        assertEquals("10-10-2024", new SimpleDateFormat("dd-MM-yyyy").format(mesa.getFechaInicioInscripcion()), "La fecha de inicio de inscripción de la mesa debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetFechaFinInscripcionMesa() {
        conexion.setFechaFinInscripcionMesa(1, "15-10-2024");
        Mesa mesa = conexion.getMesaById(1);
        assertEquals("15-10-2024", new SimpleDateFormat("dd-MM-yyyy").format(mesa.getFechaFinInscripcion()), "La fecha de fin de inscripción de la mesa debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetHabilitadoMesa() {
        conexion.setHabilitadoMesa(1, false);
        Mesa mesa = conexion.getMesaById(1);
        assertFalse(mesa.getHabilitado(), "La mesa debería estar deshabilitada");
    }

}
