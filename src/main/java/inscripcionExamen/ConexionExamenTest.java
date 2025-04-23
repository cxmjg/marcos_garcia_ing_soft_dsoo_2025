package inscripcionExamen;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConexionExamenTest {

	private static Conexion conexion;
    
    @BeforeAll
    public static void setup() {
        conexion = new Conexion();
        conexion.crearPrueba(); // Poblar la base de datos con datos iniciales
        
        
    }

    @Test
    @Tag("Conexion")
    void testGetExamenById() {
        Examen examen = conexion.getExamenById(1);
        assertNotNull(examen, "El examen no debería ser nulo");
        assertEquals(1, examen.getId(), "El ID del examen debería ser 1");
    }

    @Test
    @Tag("Conexion")
    void testGetExamenes() {
        List<Examen> examenes = conexion.getExamenes();
        assertNotNull(examenes, "La lista de exámenes no debería ser nula");
        assertFalse(examenes.isEmpty(), "La lista de exámenes no debería estar vacía");
    }

    @Test
    @Tag("Conexion")
    void testGetExamenesHabilitados() {
        List<Examen> examenesHabilitados = conexion.getExamenesHabilitados();
        assertNotNull(examenesHabilitados, "La lista de exámenes habilitados no debería ser nula");
        assertTrue(examenesHabilitados.stream().allMatch(Examen::isHabilitado), "Todos los exámenes deberían estar habilitados");
    }

    @Test
    @Tag("Conexion")
    void testCrearExamen() {
        Usuario docenteTitular = conexion.getUsuarioById(1);
        Usuario docenteVocal = conexion.getUsuarioById(2);
        Materia materia = conexion.getMateriaById(1);
        Mesa mesa = conexion.getMesaById(1);

        conexion.crearExamen("15-10-2024", true, docenteTitular, docenteVocal, materia, mesa);
        List<Examen> examenes = conexion.getExamenes();
        
        assertTrue(
            examenes.stream().anyMatch(ex -> ex.getMateria().getId() == materia.getId() && ex.getMesa().getId() == mesa.getId()),
            "El nuevo examen debería haberse creado correctamente"
        );
    }

    @Test
    @Tag("Conexion")
    void testSetHabilitadoExamen() {
        conexion.setHabilitadoExamen(1, false);
        Examen examen = conexion.getExamenById(1);
        assertFalse(examen.isHabilitado(), "El examen debería estar deshabilitado");
    }

    @Test
    @Tag("Conexion")
    void testSetFechaExamen() {
        conexion.setFechaExamen(1, "20-10-2024");
        Examen examen = conexion.getExamenById(1);
        assertEquals("20-10-2024", new SimpleDateFormat("dd-MM-yyyy").format(examen.getFecha()), "La fecha del examen debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetMesaExamen() {
        Mesa nuevaMesa = conexion.getMesaById(2);
        conexion.setMesaExamen(1, nuevaMesa);
        Examen examen = conexion.getExamenById(1);
        assertEquals(2, examen.getMesa().getId(), "La mesa del examen debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetMateriaExamen() {
        Materia nuevaMateria = conexion.getMateriaById(2);
        conexion.setMateriaExamen(1, nuevaMateria);
        Examen examen = conexion.getExamenById(1);
        assertEquals(2, examen.getMateria().getId(), "La materia del examen debería haber sido actualizada");
    }

    @Test
    @Tag("Conexion")
    void testSetPresidenteExamen() {
        Usuario nuevoPresidente = conexion.getUsuarioById(3);
        conexion.setPresidenteExamen(1, nuevoPresidente);
        Examen examen = conexion.getExamenById(1);
        assertEquals(3, examen.getDocenteTitular().getId(), "El presidente del examen debería haber sido actualizado");
    }

    @Test
    @Tag("Conexion")
    void testSetVocalExamen() {
        Usuario nuevoVocal = conexion.getUsuarioById(4);
        conexion.setVocalExamen(1, nuevoVocal);
        Examen examen = conexion.getExamenById(1);
        assertEquals(4, examen.getDocenteVocal().getId(), "El vocal del examen debería haber sido actualizado");
    }
}
